package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.FamS;

/**
 * Renders fam s output for display.
 *
 * @author Richard Schoeller
 */
public final class FamSRenderer extends AbstractLinkRenderer<FamS> {
    /**
     * Creates a new FamSRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public FamSRenderer(final FamS gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
