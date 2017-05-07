package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.FamC;

/**
 * Render a FamC.
 *
 * @author Dick Schoeller
 */
public final class FamCRenderer extends AbstractLinkRenderer<FamC> {
    /**
     * @param gedObject the FamC that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public FamCRenderer(final FamC gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
