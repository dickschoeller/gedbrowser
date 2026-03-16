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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return NAME;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    public String getVersion() {
        return VERSION;
    }

    /**
     * Gets the maintainer email.
     *
     * @return the maintainer email
     */
    public String getMaintainerEmail() {
        return MAINTAINER_EMAIL;
    }

    /**
     * Gets the maintainer name.
     *
     * @return the maintainer name
     */
    public String getMaintainerName() {
        return MAINTAINER_NAME;
    }

    /**
     * Gets the application u r l.
     *
     * @return the application u r l
     */
    public String getApplicationURL() {
        return APPLICATION_URL;
    }


    /**
     * Gets the info map.
     *
     * @return the info map
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
