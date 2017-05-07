package org.schoellerfamily.gedbrowser.renderer.application;

import java.util.HashMap;
import java.util.Map;

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
        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("name", getApplicationName());
        infoMap.put("version", getVersion());
        infoMap.put("URL", getApplicationURL());
        final Map<String, Object> maintainerMap = getMaintainerMap();
        infoMap.put("maintainer", maintainerMap);
        return infoMap;
    }


    /**
     * @return the maintainer part of the info
     */
    default Map<String, Object> getMaintainerMap() {
        final Map<String, Object> maintainerMap = new HashMap<>();
        maintainerMap.put("name", getMaintainerName());
        maintainerMap.put("email", getMaintainerEmail());
        return maintainerMap;
    }
}
