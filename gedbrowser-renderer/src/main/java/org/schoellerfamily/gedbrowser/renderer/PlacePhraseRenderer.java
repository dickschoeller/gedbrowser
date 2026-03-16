package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * Renders place phrase output for display.
 *
 * @author Richard Schoeller
 */
public class PlacePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the PlaceRenderer that is using this helper.
     */
    private final transient PlaceRenderer placeRenderer;

    /**
     * Creates a new PlacePhraseRenderer.
     *
     * @param placeRenderer the place renderer
     */
    protected PlacePhraseRenderer(final PlaceRenderer placeRenderer) {
        this.placeRenderer = placeRenderer;
    }

    /**
     * Executes render as phrase.
     *
     * @return the resulting string
     */
    @Override
    public final String renderAsPhrase() {
        final Place place = placeRenderer.getGedObject();
        return RenderingContextRenderer.escapeString(place.getString());
    }
}
