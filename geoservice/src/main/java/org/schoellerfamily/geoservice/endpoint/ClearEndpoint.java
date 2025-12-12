package org.schoellerfamily.geoservice.endpoint;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Component
@Endpoint(id = "clear")
@Slf4j
public class ClearEndpoint extends BaseGeoCodeEndpoint {
    public ClearEndpoint(final GeoCode gcc) {
        super(gcc);
    }

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
        log.info("Invoke clear");
        getGeoCode().clear();
    }
}
