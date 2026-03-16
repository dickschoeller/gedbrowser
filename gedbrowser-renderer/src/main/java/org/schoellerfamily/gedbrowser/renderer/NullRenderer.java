package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders nothing, used when we don't know what to display.
 *
 * @author Dick Schoeller
 */
public final class NullRenderer extends GedRenderer<GedObject> {
    /**
     * Creates a new NullRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public NullRenderer(final GedObject gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
