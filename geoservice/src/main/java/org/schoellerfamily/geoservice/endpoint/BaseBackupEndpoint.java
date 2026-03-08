package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
@Slf4j
public abstract class BaseBackupEndpoint {
    /** */
    private final GeoCode gcc;

    /** */
    private final String backupFileName;

    /**
     * Do the backup related action.
     *
     * @param backupFile which backup file mean
     * @throws IOException if the file can't be accessed
     */
    public abstract void action(File backupFile) throws IOException;

    /**
     * Return an identifier for logging. Subclasses currently implement
     * {@code getId()} so keep that contract via an abstract method here.
     *
     * @return endpoint id
     */
    protected abstract String getId();

    /**
     * Do the invocation and return messages.
     *
     * @return messages
     */
    public final List<String> invoke() {
        log.info("Invoke {} from {}", getId(), backupFileName);
        final List<String> messages = new ArrayList<>();
        try {
            action(new File(backupFileName));
            messages.add("Invoccation of " + getId() + " succeeded to/from "
                    + backupFileName);
        } catch (IOException e) {
            messages.add("Invoccation of " + getId() + " failed to/from "
                    + backupFileName);
            messages.add("Exception: " + e.getMessage());
            log.error("Invoccation of {} failed", getId(), e);
        }
        messages.add(gcc.size() + " locations in the cache");
        messages.add(
                gcc.size() - gcc.countNotFound()
                + " geocoded locations in cache");
        return messages;
    }
}
