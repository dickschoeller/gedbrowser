package org.schoellerfamily.geoservice.controller;

import java.util.Map;

/**
 * @author Dick Schoeller
 */
public final class ApplicationInfo {
    /** */
    private static final String NAME = "gedbrowser-geoservice";
    /** */
    private static final String VERSION = "1.3.0-RC3-SNAPSHOT";
    /** */
    private static final String APPLICATION_URL =
            "https://github.com/dickschoeller/gedbrowser";
    /** */
    private static final String MAINTAINER_NAME = "Richard Schoeller";
    /** */
    private static final String MAINTAINER_EMAIL = "schoeller@comcast.net";

    /**
     * @return the application name.
     */
    public String getName() {
        return NAME;
    }

    /**
     * @return the version string.
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * @return the maintainer's email address.
     */
    public String getMaintainerEmail() {
        return MAINTAINER_EMAIL;
    }

    /**
     * @return the maintainer's name.
     */
    public String getMaintainerName() {
        return MAINTAINER_NAME;
    }

    /**
     * @return the URL to learn more about the application.
     */
    public String getApplicationURL() {
        return APPLICATION_URL;
    }


    /**
     * @return the map that feeds the contributor.
     */
    public Map<String, Object> getInfoMap() {
        return Map.of(
            "name", NAME,
            "version", VERSION,
            "URL", APPLICATION_URL,
            "maintainer", getMaintainerMap());
    }

    private Map<String, Object> getMaintainerMap() {
        return Map.of(
            "name", MAINTAINER_NAME,
            "email", MAINTAINER_EMAIL);
    }
}
