package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.FamC;

/**
 * Render a FamC.
 *
 * @author Dick Schoeller
 */
public final class FamCRenderer extends AbstractLinkRenderer<FamC> {
    /**
     * Creates a new FamCRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public FamCRenderer(final FamC gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
