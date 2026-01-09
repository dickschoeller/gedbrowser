package org.schoellerfamily.gedbrowser.endpoint;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
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
@Slf4j
@RequiredArgsConstructor
public class ApplicationInfoContributor implements InfoContributor {

    /** */
    private final ApplicationInfo appInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    public void contribute(final Builder builder) {
        log.info("Contribute to info");
        builder.withDetail("app", appInfo.getInfoMap());
    }
}
