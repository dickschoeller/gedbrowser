package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Renders nothing, used when we don't know what to display.
 *
 * @author Dick Schoeller
 */
public final class NullRenderer extends GedRenderer<GedObject> {
    /**
     * @param gedObject The GedObject that we are going to render.
     * @param rendererFactory
     *            The factory that creates the renderers for the attributes.
     * @param renderingContext the context that we are rendering in
     * @param provider calendar provider
     */
    public NullRenderer(final GedObject gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
    }
}
