package org.schoellerfamily.geoservice.controller;

/**
 * @author Dick Schoeller
 */
public final class ApplicationInfo {
    /**
     * @return the application name.
     */
    public String getName() {
        return "gedbrowser-geoservice";
    }

    /**
     * @return the version string.
     */
    public String getVersion() {
<<<<<<< d9a8f74d12ba876b790816b5dbac255281bfa1e0
        return "1.1.0";
=======
        return "1.1.1-SNAPSHOT";
>>>>>>> Create development branch with snapshot version
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
        return "Richard Schoeller";
    }

    /**
     * @return the URL to learn more about the application.
     */
    public String getApplicationURL() {
        return "https://github.com/dickschoeller/gedbrowser";
    }
}
