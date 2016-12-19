package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
public final class DefaultRenderer extends GedRenderer<GedObject> {
    /**
     * This constructor is public for testing purposes only. Do
     * not try to call it outside of the context of the rendering
     * engine.
     *
     * @param gedObject the gedObject from the parent renderer
     * @param rendererFactory the renderFactory from the parent renderer
     * @param renderingContext the context that we are rendering in
     */
    public DefaultRenderer(final GedObject gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
