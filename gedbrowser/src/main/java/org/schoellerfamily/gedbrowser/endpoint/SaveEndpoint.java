package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "save")
@Slf4j
public class SaveEndpoint {

    /**
     * Exposed actuator read operation for save.
     *
     * @return messages
     */
    @ReadOperation
    public final List<String> invoke() {
        log.info("Invoke save");
        final List<String> messages = new ArrayList<>();
        messages.add("Currently a NO-OP");
        return messages;
    }
}
