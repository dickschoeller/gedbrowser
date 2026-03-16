package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;

/**
 * Renders error output for display.
 *
 * @author Richard Schoeller
 */
public final class ErrorRenderer extends ApplicationInfoRenderer {
    /**
     * Creates a new ErrorRenderer.
     *
     * @param applicationInfo the application info
     */
    public ErrorRenderer(final ApplicationInfo applicationInfo) {
        super(applicationInfo);
    }
}
