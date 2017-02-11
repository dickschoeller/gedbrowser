package org.schoellerfamily.gedbrowser.renderer;

/**
 * This interface is for obtaining commonly interesting information about the
 * running application.
 *
 * @author Dick Schoeller
 */
public interface ApplicationInfo {

    /**
     * @return the application name.
     */
    String getName();

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

}
