package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;

/**
 * Renders abstract link output for display.
 *
 * @author Richard Schoeller
 * @param <T> The object type to render.
 */
public abstract class AbstractLinkRenderer<T extends AbstractLink> extends GedRenderer<T> {
    /**
     * Creates a new AbstractLinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    protected AbstractLinkRenderer(final T gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
