package org.schoellerfamily.gedbrowser.api.controller;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Dick Schoeller
 */
public final class ApplicationInfoImpl implements ApplicationInfo {
    /** */
    @Value("${gedbrowser.maintainer.email:schoeller@comcast.net}")
    private transient String maintainerEmail;

    /** */
    @Value("${gedbrowser.maintainer.name:Richard Schoeller}")
    private transient String maintainerName;

    /** */
    @Value("${gedbrowser.applicationurl:"
            + "https://github.com/dickschoeller/gedbrowser}")
    private transient String applicationUrl;

    /** */
    @Value("${gedbrowser.homeurl:http://www.schoellerfamily.org/}")
    private transient String homeUrl;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicationName() {
        return "gedbrowser-api";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersion() {
        return GedObject.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaintainerEmail() {
        return maintainerEmail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaintainerName() {
        return maintainerName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicationURL() {
        return applicationUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHomeURL() {
        return homeUrl;
    }
}
