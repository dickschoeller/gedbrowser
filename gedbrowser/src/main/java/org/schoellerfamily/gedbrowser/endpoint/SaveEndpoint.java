package org.schoellerfamily.gedbrowser.endpoint;

import java.util.List;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the save endpoint.
 *
 * @author Richard Schoeller
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
        return List.of("Currently a NO-OP");
    }
}
