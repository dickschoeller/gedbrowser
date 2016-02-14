package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Child;

/**
 * Render a Child.
 *
 * @author Dick Schoeller
 */
public final class ChildRenderer extends AbstractLinkRenderer<Child> {
    /**
     * @param gedObject the Child that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public ChildRenderer(final Child gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
