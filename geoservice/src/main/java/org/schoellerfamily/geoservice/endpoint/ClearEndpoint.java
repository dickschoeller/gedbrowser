package org.schoellerfamily.geoservice.endpoint;

import java.util.List;

import org.schoellerfamily.geoservice.persistence.GeoCode;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import lombok.extern.slf4j.Slf4j;



/**
 * Exposes operations for the clear endpoint.
 *
 * @author Richard Schoeller
 */
@Controller("/actuator")
@Slf4j
public class ClearEndpoint extends BaseGeoCodeEndpoint {
    /**
     * Creates a new ClearEndpoint.
     *
     * @param gcc the gcc
     */
    public ClearEndpoint(final GeoCode gcc) {
        super(gcc);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Override
    public final String getId() {
        return "clear";
    }

    /**
     * Returns the list.
     *
     * @return the resulting list
     */
    @Get("/clear")
    public List<String> invokeEndpoint() {
        return super.invoke();
    }

    /**
     * Executes geo code action.
     */
    @Override
    public final void geoCodeAction() {
        log.info("Invoke clear");
        getGeoCode().clear();
    }
}
