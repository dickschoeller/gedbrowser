package org.schoellerfamily.gedbrowser.renderer.application;

import java.util.Map;

/**
 * @author Dick Schoeller
 */
public interface ApplicationInfoFacade extends ApplicationInfo {
    /**
     * @return the contained application information object
     */
    ApplicationInfo getApplicationInfo();

    /**
     * {@inheritDoc}
     */
    @Override
    default String getApplicationName() {
        return getApplicationInfo().getApplicationName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getVersion() {
        return getApplicationInfo().getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getMaintainerEmail() {
        return getApplicationInfo().getMaintainerEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getMaintainerName() {
        return getApplicationInfo().getMaintainerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getApplicationURL() {
        return getApplicationInfo().getApplicationURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default String getHomeURL() {
        return getApplicationInfo().getHomeURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Map<String, Object> getInfoMap() {
        return getApplicationInfo().getInfoMap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Map<String, Object> getMaintainerMap() {
        return getApplicationInfo().getMaintainerMap();
    }

}
