package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;

import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Dick Schoeller
 */
@Component
public class BackupEndpoint extends BaseBackupEndpoint {
    /** */
    @Autowired
    private transient GeoCodeBackup backupManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getId() {
        return "backup";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(final File backupFile)
            throws JsonParseException, JsonMappingException, IOException {
        backupManager.backup(backupFile);
    }
}
