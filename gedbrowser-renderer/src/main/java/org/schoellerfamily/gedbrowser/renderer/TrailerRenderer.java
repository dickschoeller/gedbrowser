package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * Renders trailer output for display.
 *
 * @author Richard Schoeller
 */
public final class TrailerRenderer extends GedRenderer<Trailer> {
    /**
     * Creates a new TrailerRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public TrailerRenderer(final Trailer gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
