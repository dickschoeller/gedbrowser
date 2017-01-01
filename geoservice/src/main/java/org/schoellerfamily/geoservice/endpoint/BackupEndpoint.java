package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class BackupEndpoint implements Endpoint<List<String>> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GeoCodeBackup backupManager;

    /** */
    @Value("${geoservice.backupfile:"
            + "/var/lib/gedbrowser/geoservice-backup.json}")
    private transient String backupFile;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "backup";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> invoke() {
        logger.info("Invoke backup to " + backupFile);
        final List<String> messages = new ArrayList<>();
        try {
            backupManager.backup(new File(backupFile));
            messages.add("Backup succeed to " + backupFile);
        } catch (IOException e) {
            messages.add("Backup failed from " + backupFile);
            messages.add("Exception: " + e.getMessage());
            logger.error("Backup failed", e);
        }
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSensitive() {
        return true;
    }
}
