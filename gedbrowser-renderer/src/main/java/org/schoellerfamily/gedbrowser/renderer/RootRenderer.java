package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Render a Root.
 *
 * @author Dick Schoeller
 */
public final class RootRenderer extends GedRenderer<Root> {
    /**
     * @param gedObject the Root that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public RootRenderer(final Root gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
    }
}
