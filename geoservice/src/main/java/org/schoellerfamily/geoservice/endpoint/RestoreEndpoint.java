package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;

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
@Endpoint(id = "restore")
public class RestoreEndpoint extends BaseBackupEndpoint {
    /** */
    private GeoCodeBackup backupManager;

    public RestoreEndpoint(final GeoCodeBackup backupManager, final GeoCode gcc,
        @Value("${geoservice.backupfile:/var/lib/gedbrowser/geoservice-backup.json}")
        final String backupFileName
    ) {
        super(gcc, backupFileName);
        this.backupManager = backupManager;
    }

    /**
     * {@inheritDoc}
     */
    public final String getId() {
        return "restore";
    }

    @ReadOperation
    public java.util.List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void action(final File backupFile)
            throws JsonParseException, JsonMappingException, IOException {
        backupManager.recover(backupFile);
    }
}
