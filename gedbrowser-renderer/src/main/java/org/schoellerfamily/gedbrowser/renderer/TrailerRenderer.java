package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * Render a Submittor.
 *
 * @author Dick Schoeller
 */
public final class TrailerRenderer extends GedRenderer<Trailer> {
    /**
     * @param gedObject the Trailer that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public TrailerRenderer(final Trailer gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
    }
}
