package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Husband;

/**
 * Render a Husband.
 *
 * @author Dick Schoeller
 */
public final class HusbandRenderer extends AbstractLinkRenderer<Husband> {
    /**
     * @param gedObject the Husband that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public HusbandRenderer(final Husband gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
