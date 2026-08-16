package org.schoellerfamily.geoservice.endpoint;

import java.util.Map;

import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import lombok.extern.slf4j.Slf4j;



/**
 * Contributes application info information to a larger response.
 *
 * @author Richard Schoeller
 */
@Controller("/actuator")
@Slf4j
public final class ApplicationInfoContributor {
    /** */
    private final ApplicationInfo appInfo;

    /**
     * Create info endpoint.
     *
     * @param appInfo application info provider
     */
    public ApplicationInfoContributor(final ApplicationInfo appInfo) {
        this.appInfo = appInfo;
    }

    /**
        * Build the info payload.
     *
        * @return info endpoint payload
     */
    @Get("/info")
    public Map<String, Object> info() {
        log.info("Contribute to info");
        return Map.of("app", appInfo.getInfoMap());
    }
}
