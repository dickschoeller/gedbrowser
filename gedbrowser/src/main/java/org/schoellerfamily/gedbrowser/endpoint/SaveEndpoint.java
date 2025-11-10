package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "save")
public class SaveEndpoint {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Exposed actuator read operation for save.
     *
     * @return messages
     */
    @ReadOperation
    public final List<String> invoke() {
        logger.info("Invoke save");
        final List<String> messages = new ArrayList<>();
        messages.add("Currently a NO-OP");
        return messages;
    }
}
