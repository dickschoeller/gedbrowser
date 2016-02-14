package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Render a Wife.
 *
 * @author Dick Schoeller
 */
public final class WifeRenderer extends AbstractLinkRenderer<Wife> {
    /**
     * @param gedObject the Wife that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public WifeRenderer(final Wife gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
