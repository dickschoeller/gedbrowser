package org.schoellerfamily.geoservice.endpoint;

import org.schoellerfamily.geoservice.controller.ApplicationInfo;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Add our application specific information to the info endpoint.
 *
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
public final class ApplicationInfoContributor implements InfoContributor {
    /** */
    private final ApplicationInfo appInfo;

    /**
     * Executes contribute.
     *
     * @param builder the builder
     */
    @Override
    public void contribute(final Builder builder) {
        log.info("Contribute to info");
        builder.withDetail("app", appInfo.getInfoMap());
    }
}
