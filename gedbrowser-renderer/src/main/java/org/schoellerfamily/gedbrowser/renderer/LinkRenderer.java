package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Link;

/**
 * Render a Link.
 *
 * @author Dick Schoeller
 */
public final class LinkRenderer extends AbstractLinkRenderer<Link> {
    /**
     * Creates a new LinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public LinkRenderer(final Link gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
