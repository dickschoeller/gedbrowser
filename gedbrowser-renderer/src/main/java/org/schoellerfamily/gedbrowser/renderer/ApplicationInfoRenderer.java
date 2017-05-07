package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * @author Dick Schoeller
 */
public abstract class ApplicationInfoRenderer implements Renderer {
    /** */
    private final ApplicationInfo applicationInfo;

    /**
     * Constructor.
     *
     * @param applicationInfo the application info
     */
    public ApplicationInfoRenderer(final ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getApplicationName() {
        return applicationInfo.getApplicationName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getApplicationURL() {
        return applicationInfo.getApplicationURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHomeUrl() {
        return applicationInfo.getHomeURL();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaintainerEmail() {
        return applicationInfo.getMaintainerEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMaintainerName() {
        return applicationInfo.getMaintainerName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVersion() {
        return applicationInfo.getVersion();
    }
}
