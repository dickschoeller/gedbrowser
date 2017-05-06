package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.FamS;

/**
 * Render a FamS.
 *
 * @author Dick Schoeller
 */
public final class FamSRenderer extends AbstractLinkRenderer<FamS> {
    /**
     * @param gedObject the GedObject that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public FamSRenderer(final FamS gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
