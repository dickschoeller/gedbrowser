package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * @author Dick Schoeller
 */
public class PlacePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the PlaceRenderer that is using this helper.
     */
    private final transient PlaceRenderer placeRenderer;

    /**
     * Constructor.
     *
     * @param placeRenderer the renderer that this is associated with.
     */
    protected PlacePhraseRenderer(final PlaceRenderer placeRenderer) {
        this.placeRenderer = placeRenderer;
    }

    @Override
    public final String renderAsPhrase() {
        final Place place = placeRenderer.getGedObject();
        return GedRenderer.escapeString(place.getString());
    }
}
