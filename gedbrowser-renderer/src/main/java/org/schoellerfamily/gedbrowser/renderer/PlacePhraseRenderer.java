package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Place;

import lombok.RequiredArgsConstructor;

/**
 * Renders place phrase output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PlacePhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the PlaceRenderer that is using this helper.
     */
    private final PlaceRenderer placeRenderer;

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
