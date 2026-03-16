package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public abstract class ApplicationInfoRenderer implements Renderer {
    /** */
    private final ApplicationInfo applicationInfo;

    /**
     * Creates a new ApplicationInfoRenderer.
     *
     * @param applicationInfo the application info
     */
    protected ApplicationInfoRenderer(final ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    /**
     * Gets the application name.
     *
     * @return the application name
     */
    @Override
    public final String getApplicationName() {
        return applicationInfo.getApplicationName();
    }

    /**
     * Gets the application u r l.
     *
     * @return the application u r l
     */
    @Override
    public final String getApplicationURL() {
        return applicationInfo.getApplicationURL();
    }

    /**
     * Gets the home url.
     *
     * @return the home url
     */
    @Override
    public final String getHomeUrl() {
        return applicationInfo.getHomeURL();
    }

    /**
     * Gets the maintainer email.
     *
     * @return the maintainer email
     */
    @Override
    public final String getMaintainerEmail() {
        return applicationInfo.getMaintainerEmail();
    }

    /**
     * Gets the maintainer name.
     *
     * @return the maintainer name
     */
    @Override
    public final String getMaintainerName() {
        return applicationInfo.getMaintainerName();
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @Override
    public final String getVersion() {
        return applicationInfo.getVersion();
    }
}
