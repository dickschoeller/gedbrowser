package org.schoellerfamily.gedbrowser.endpoint;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Contributes application info information to a larger response.
 *
 * @author Richard Schoeller
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationInfoContributor implements InfoContributor {

    /** */
    private final ApplicationInfo appInfo;

    /**
     * Contribute to the application information.
     *
     * @param builder the builder to add information to
     */
    @Override
    public void contribute(final Builder builder) {
        log.info("Contribute to info");
        builder.withDetail("app", appInfo.getInfoMap());
    }
}
