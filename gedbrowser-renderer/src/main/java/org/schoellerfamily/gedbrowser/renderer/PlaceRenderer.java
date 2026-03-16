package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * Renders place output for display.
 *
 * @author Richard Schoeller
 */
public final class PlaceRenderer extends GedRenderer<Place> {
    /**
     * Creates a new PlaceRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public PlaceRenderer(final Place gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new PlaceListItemRenderer(this));
        setPhraseRenderer(new PlacePhraseRenderer(this));
    }
}
