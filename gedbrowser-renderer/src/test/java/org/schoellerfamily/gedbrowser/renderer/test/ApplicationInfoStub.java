package org.schoellerfamily.gedbrowser.renderer.test;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public final class ApplicationInfoStub implements ApplicationInfo {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicationName() {
        return "gedbrowser";
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
        return "schoeller@comcast.net";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaintainerName() {
        return "Richard Schoeller";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicationURL() {
        return "https://github.com/dickschoeller/gedbrowser";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHomeURL() {
        return "http://www.schoellerfamily.org/";
    }
}
