package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public final class DefaultRenderer extends GedRenderer<GedObject> {
    /**
     * @param gedObject the gedObject from the parent renderer
     * @param rendererFactory the renderFactory from the parent renderer
     * @param renderingContext the context that we are rendering in
     */
    protected DefaultRenderer(final GedObject gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
