package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.Endpoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Dick Schoeller
 */
public abstract class BaseBackupEndpoint implements Endpoint<List<String>> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private GeoCode gcc;

    /** */
    @Value("${geoservice.backupfile:"
            + "/var/lib/gedbrowser/geoservice-backup.json}")
    private transient String backupFileName;

    /**
     * Do the backup related action.
     *
     * @param backupFile which backup file mean
     * @throws JsonParseException if the JSON doesn't parse
     * @throws JsonMappingException if JSON won't map to the data types
     * @throws IOException if the file can't be accessed
     */
    abstract void action(File backupFile)
            throws JsonParseException, JsonMappingException, IOException;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<String> invoke() {
        logger.info("Invoke " + getId() + " from " + backupFileName);
        final List<String> messages = new ArrayList<>();
        try {
            action(new File(backupFileName));
            messages.add("Invoccation of " + getId() + " succeeded to/from "
                    + backupFileName);
        } catch (IOException e) {
            messages.add("Invoccation of " + getId() + " failed to/from "
                    + backupFileName);
            messages.add("Exception: " + e.getMessage());
            logger.error("Invoccation of " + getId() + " failed", e);
        }
        messages.add(gcc.size() + " locations in the cache");
        messages.add(
                gcc.size() - gcc.countNotFound()
                + " geocoded locations in cache");
        return messages;
    }
}
