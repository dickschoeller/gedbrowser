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
     * Constructor.
     *
     * @param placeRenderer the renderer that this is associated with.
     */
    protected PlaceListItemRenderer(final PlaceRenderer placeRenderer) {
        this.placeRenderer = placeRenderer;
    }

    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        GedRenderer.renderPad(builder, pad, newLine);
        builder.append(getListItemContents());
        return builder;
    }

    @Override
    public final String getListItemContents() {
        return placeRenderer.renderAsPhrase();
    }
}
