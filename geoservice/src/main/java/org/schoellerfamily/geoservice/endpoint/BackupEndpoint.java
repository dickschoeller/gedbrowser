package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "backup")
public class BackupEndpoint extends BaseBackupEndpoint {
    /** */
    private final GeoCodeBackup backupManager;

    /**
     * Constructor.
     *
     * @param backupManager
     * @param gcc
     * @param backupFileName
     */
    public BackupEndpoint(final GeoCodeBackup backupManager, final GeoCode gcc,
        @Value("${geoservice.backupfile:/var/lib/gedbrowser/geoservice-backup.json}")
        final String backupFileName
    ) {
        super(gcc, backupFileName);
        this.backupManager = backupManager;
    }

    @Override
    public final String getId() {
        return "backup";
    }

    /**
     * @return the list of strings
     */
    @ReadOperation
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    @Override
    public final void action(final File backupFile)
            throws JsonParseException, JsonMappingException, IOException {
        backupManager.backup(backupFile);
    }
}
