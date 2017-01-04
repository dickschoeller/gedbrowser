package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class RestoreEndpoint implements Endpoint<List<String>> {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private GeoCode gcc;

    /** */
    @Autowired
    private transient GeoCodeBackup backupManager;

    /** */
    @Value("${geoservice.backupfile:/var/lib/gedbrowser/geoservice}")
    private transient String backupFile;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getId() {
        return "restore";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<String> invoke() {
        logger.info("Invoke restore from " + backupFile);
        final List<String> messages = new ArrayList<>();
        try {
            backupManager.recover(new File(backupFile));
            messages.add("Restore succeed from " + backupFile);
        } catch (IOException e) {
            messages.add("Restore failed from " + backupFile);
            messages.add("Exception: " + e.getMessage());
            logger.error("Backup failed", e);
        }
        messages.add(gcc.size() + " locations in the cache");
        messages.add(
                gcc.size() - gcc.countNotFound()
                + " geocoded locations in cache");
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isSensitive() {
        return true;
    }
}