package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Child;

/**
 * Render a Child.
 *
 * @author Dick Schoeller
 */
public final class ChildRenderer extends AbstractLinkRenderer<Child> {
    /**
     * Creates a new ChildRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public ChildRenderer(final Child gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
