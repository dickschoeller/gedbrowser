package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * Render a Place.
 *
 * @author Dick Schoeller
 */
public final class PlaceRenderer extends GedRenderer<Place> {
    /**
     * @param gedObject the Place that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public PlaceRenderer(final Place gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setListItemRenderer(new PlaceListItemRenderer(this));
        setPhraseRenderer(new PlacePhraseRenderer(this));
    }
}
