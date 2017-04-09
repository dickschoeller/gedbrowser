package org.schoellerfamily.gedbrowser.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
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
public final class ApplicationInfoContributor implements InfoContributor {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    public void contribute(final Builder builder) {
        logger.info("Contribute to info");
        builder.withDetail("app", appInfo.getInfoMap());
    }
}
