package org.schoellerfamily.gedbrowser.controller;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public final class ApplicationInfo {
    /**
     * @return the application name.
     */
    public String getName() {
        return "GEDbrowser";
    }

    /**
     * @return the version string.
     */
    public String getVersion() {
        return GedObject.VERSION;
    }

    /**
     * @return the maintainer's email address.
     */
    public String getMaintainerEmail() {
        return "schoeller@comcast.net";
    }

    /**
     * @return the maintainer's name.
     */
    public String getMaintainerName() {
        return "Dick Schoeller";
    }

    /**
     * @return the URL to learn more about the application.
     */
    public String getApplicationURL() {
        return "http://www.schoellerfamily.org/software/gedbrowser.html";
    }
}
