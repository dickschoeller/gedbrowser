package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders place list item output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PlaceListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the PlaceRenderer that is using this helper.
     */
    private final PlaceRenderer placeRenderer;

    /**
     * Executes render as list item.
     *
     * @param builder the builder
     * @param newLine the new line
     * @param pad the pad
     * @return the resulting string builder
     */
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        GedRenderer.renderPad(builder, pad, newLine);
        builder.append(getListItemContents());
        return builder;
    }

    /**
     * Returns the list item contents.
     *
     * @return the list item contents
     */
    @Override
    public final String getListItemContents() {
        return placeRenderer.renderAsPhrase();
    }
}
