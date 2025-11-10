package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "restore")
public class RestoreEndpoint {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    /**
     * Exposed actuator read operation for restore.
     *
     * @return messages
     */
    @ReadOperation
    public final List<String> invoke() {
        final List<String> messages = new ArrayList<>();
        logger.info("Invoke restore");
        loader.reloadAll();
        messages.add("Reloaded " + loader.details().size() + " datasets");
        return messages;
    }
}
