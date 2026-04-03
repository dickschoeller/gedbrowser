package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Renders wife output for display.
 *
 * @author Richard Schoeller
 */
public final class WifeRenderer extends AbstractLinkRenderer<Wife> {
    /**
     * Creates a new WifeRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public WifeRenderer(final Wife gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
