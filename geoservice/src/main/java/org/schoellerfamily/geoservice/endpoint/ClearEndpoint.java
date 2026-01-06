package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

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
    /**
     * Constructor.
     *
     * @param gcc a geocode
     */
    public ClearEndpoint(final GeoCode gcc) {
        super(gcc);
    }

    @Override
    public final String getId() {
        return "clear";
    }

    /**
     * @return the list of strings
     */
    @ReadOperation
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    @Override
    public final void geoCodeAction() {
        log.info("Invoke clear");
        getGeoCode().clear();
    }
}
