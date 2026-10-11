package org.schoellerfamily.gedbrowser.renderer.test;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * Provides a stub implementation for application info.
 *
 * @author Richard Schoeller
 */
public final class ApplicationInfoStub implements ApplicationInfo {
    /**
     * Returns the application name.
     *
     * @return the application name
     */
    @Override
    public String getApplicationName() {
        return "gedbrowser";
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    @Override
    public String getVersion() {
        return GedObject.VERSION;
    }

    /**
     * Returns the maintainer email.
     *
     * @return the maintainer email
     */
    @Override
    public String getMaintainerEmail() {
        return "schoeller@comcast.net";
    }

    /**
     * Returns the maintainer name.
     *
     * @return the maintainer name
     */
    @Override
    public String getMaintainerName() {
        return "Richard Schoeller";
    }

    /**
     * Returns the application u r l.
     *
     * @return the application u r l
     */
    @Override
    public String getApplicationURL() {
        return "https://github.com/dickschoeller/gedbrowser";
    }

    /**
     * Returns the home u r l.
     *
     * @return the home u r l
     */
    @Override
    public String getHomeURL() {
        return "http://www.schoellerfamily.org/";
    }
}
