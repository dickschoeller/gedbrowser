package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "restore")
@Slf4j
public class RestoreEndpoint {

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Exposed actuator read operation for restore.
     *
     * @return messages
     */
    @ReadOperation
    public final List<String> invoke() {
        final List<String> messages = new ArrayList<>();
        log.info("Invoke restore");
        loader.reloadAll(repositoryManager);
        messages.add("Reloaded " + loader.details(repositoryManager).size() + " datasets");
        return messages;
    }
}
