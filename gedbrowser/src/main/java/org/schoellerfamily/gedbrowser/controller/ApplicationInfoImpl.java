package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of ApplicationInfo that gets its data from application
 * properties.
 *
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
public final class ApplicationInfoImpl implements ApplicationInfo {
    /** */
    @Value("${gedbrowser.maintainer.email:schoeller@comcast.net}")
    private final String maintainerEmail;

    /** */
    @Value("${gedbrowser.maintainer.name:Richard Schoeller}")
    private final String maintainerName;

    /** */
    @Value("${gedbrowser.applicationurl:"
            + "https://github.com/dickschoeller/gedbrowser}")
    private final String applicationUrl;

    /** */
    @Value("${gedbrowser.homeurl:http://www.schoellerfamily.org/}")
    private final String homeUrl;

    /**
     * Gets the application name.
     *
     * @return the application name
     */
    @Override
    public String getApplicationName() {
        return "gedbrowser";
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @Override
    public String getVersion() {
        return GedObject.VERSION;
    }

    /**
     * Gets the maintainer email.
     *
     * @return the maintainer email
     */
    @Override
    public String getMaintainerEmail() {
        return maintainerEmail;
    }

    /**
     * Gets the maintainer name.
     *
     * @return the maintainer name
     */
    @Override
    public String getMaintainerName() {
        return maintainerName;
    }

    /**
     * Gets the application u r l.
     *
     * @return the application u r l
     */
    @Override
    public String getApplicationURL() {
        return applicationUrl;
    }

    /**
     * Gets the home u r l.
     *
     * @return the home u r l
     */
    @Override
    public String getHomeURL() {
        return homeUrl;
    }
}
