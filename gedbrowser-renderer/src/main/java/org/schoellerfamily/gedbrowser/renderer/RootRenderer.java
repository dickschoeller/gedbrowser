package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Renders root output for display.
 *
 * @author Richard Schoeller
 */
public final class RootRenderer extends GedRenderer<Root> {
    /**
     * Creates a new RootRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public RootRenderer(final Root gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
