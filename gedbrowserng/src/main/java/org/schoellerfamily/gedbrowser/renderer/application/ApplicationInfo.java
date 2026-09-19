package org.schoellerfamily.gedbrowser.renderer.application;

import java.util.Map;

/**
 * Defines the contract for application info.
 *
 * @author Richard Schoeller
 */
public interface ApplicationInfo {

    /**
     * @return the application name.
     */
    String getApplicationName();

    /**
     * @return the version string.
     */
    String getVersion();

    /**
     * @return the maintainer's email address.
     */
    String getMaintainerEmail();

    /**
     * @return the maintainer's name.
     */
    String getMaintainerName();

    /**
     * @return the URL to learn more about the application.
     */
    String getApplicationURL();

    /**
     * @return the URL string that is the home page for the site
     */
    String getHomeURL();

    /**
     * @return the map that feeds the contributor.
     */
    default Map<String, Object> getInfoMap() {
        return Map.of(
            "name", getApplicationName(),
            "version", getVersion(),
            "URL", getApplicationURL(),
            "maintainer", getMaintainerMap());
    }


    /**
     * @return the maintainer part of the info
     */
    default Map<String, Object> getMaintainerMap() {
        return Map.of(
            "name", getMaintainerName(),
            "email", getMaintainerEmail());
    }
}
