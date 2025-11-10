package org.schoellerfamily.geoservice.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "clear")
public class ClearEndpoint extends BaseGeoCodeEndpoint {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    public final String getId() {
        return "clear";
    }

    @ReadOperation
    public java.util.List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void geoCodeAction() {
        logger.info("Invoke clear");
        getGeoCode().clear();
    }
}
