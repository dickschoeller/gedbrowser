package org.schoellerfamily.geoservice.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class ClearEndpoint extends BaseGeoCodeEndpoint {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getId() {
        return "clear";
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
