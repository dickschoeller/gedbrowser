package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class DateListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the DateRenderer that is using this helper.
     */
    private final transient DateRenderer dateRenderer;

    /**
     * Constructor.
     *
     * @param dateRenderer the renderer that this is associated with.
     */
    protected DateListItemRenderer(final DateRenderer dateRenderer) {
        this.dateRenderer = dateRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        final String listItemContents = getListItemContents();
        if (listItemContents.isEmpty()) {
            return builder;
        }
        GedRenderer.renderPad(builder, pad, newLine);
        builder.append("<li>");
        builder.append(listItemContents);
        builder.append("</li>\n");
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListItemContents() {
        final String phrase = dateRenderer.renderAsPhrase();
        if (phrase.isEmpty()) {
            return "";
        }
        return "<span class=\"label\">Date:</span> " + phrase;
    }
}
