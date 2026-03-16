package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class PlaceListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the PlaceRenderer that is using this helper.
     */
    private final transient PlaceRenderer placeRenderer;

    /**
     * Creates a new PlaceListItemRenderer.
     *
     * @param placeRenderer the place renderer
     */
    protected PlaceListItemRenderer(final PlaceRenderer placeRenderer) {
        this.placeRenderer = placeRenderer;
    }

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
