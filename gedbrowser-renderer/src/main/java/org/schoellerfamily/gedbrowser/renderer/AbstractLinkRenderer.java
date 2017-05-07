package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.AbstractLink;

/**
 * @author Dick Schoeller
 *
 * @param <T> The object type to render.
 */
public abstract class AbstractLinkRenderer<T extends AbstractLink> extends
        GedRenderer<T> {
    /**
     * @param gedObject the gedObject to render
     * @param rendererFactory the factory to get additional renderers
     * @param renderingContext the context that we are rendering in
     */
    public AbstractLinkRenderer(final T gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
