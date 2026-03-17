package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Husband;

/**
 * Renders husband output for display.
 *
 * @author Richard Schoeller
 */
public final class HusbandRenderer extends AbstractLinkRenderer<Husband> {
    /**
     * Creates a new HusbandRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public HusbandRenderer(final Husband gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
