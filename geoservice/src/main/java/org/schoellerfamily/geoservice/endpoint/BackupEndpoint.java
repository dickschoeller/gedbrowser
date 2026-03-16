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



/**
 * Exposes operations for the backup endpoint.
 *
 * @author Richard Schoeller
 */
@Component
@Endpoint(id = "backup")
public class BackupEndpoint extends BaseBackupEndpoint {
    /** */
    private final GeoCodeBackup backupManager;

    /**
     * Creates a new BackupEndpoint.
     *
     * @param backupManager the backup manager
     * @param gcc the gcc
     */
    public BackupEndpoint(final GeoCodeBackup backupManager, final GeoCode gcc,
        @Value("${geoservice.backupfile:/var/lib/gedbrowser/geoservice-backup.json}")
        final String backupFileName
    ) {
        super(gcc, backupFileName);
        this.backupManager = backupManager;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public final String getId() {
        return "backup";
    }

    /**
     * Returns the list.
     *
     * @return the resulting list
     */
    @ReadOperation
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * Executes action.
     *
     * @param backupFile the backup file to use
     */
    @Override
    public final void action(final File backupFile) throws IOException {
        backupManager.backup(backupFile);
    }
}
