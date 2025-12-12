package org.schoellerfamily.gedbrowser.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * Add our application specific information to the info endpoint.
 *
 * @author Dick Schoeller
 */
@Component
@Slf4j
public class ApplicationInfoContributor implements InfoContributor {

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    public void contribute(final Builder builder) {
        log.info("Contribute to info");
        builder.withDetail("app", appInfo.getInfoMap());
    }
}
