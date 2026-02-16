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
    protected ApplicationInfoRenderer(final ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    @Override
    public final String getApplicationName() {
        return applicationInfo.getApplicationName();
    }

    @Override
    public final String getApplicationURL() {
        return applicationInfo.getApplicationURL();
    }

    @Override
    public final String getHomeUrl() {
        return applicationInfo.getHomeURL();
    }

    @Override
    public final String getMaintainerEmail() {
        return applicationInfo.getMaintainerEmail();
    }

    @Override
    public final String getMaintainerName() {
        return applicationInfo.getMaintainerName();
    }

    @Override
    public final String getVersion() {
        return applicationInfo.getVersion();
    }
}
