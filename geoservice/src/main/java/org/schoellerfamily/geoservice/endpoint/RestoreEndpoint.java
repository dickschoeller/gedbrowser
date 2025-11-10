package org.schoellerfamily.geoservice.endpoint;

import java.io.File;
import java.io.IOException;

import org.schoellerfamily.geoservice.backup.GeoCodeBackup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "restore")
public class RestoreEndpoint extends BaseBackupEndpoint {

    /** */
    @Autowired
    private transient GeoCodeBackup backupManager;

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
