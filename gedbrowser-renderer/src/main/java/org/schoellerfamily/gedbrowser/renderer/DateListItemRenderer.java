package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders date list item output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public final class DateListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the DateRenderer that is using this helper.
     */
    private final DateRenderer dateRenderer;

    /**
     * Executes render as list item.
     *
     * @param builder the builder
     * @param newLine the new line
     * @param pad the pad
     * @return the resulting string builder
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
     * Returns the list item contents.
     *
     * @return the list item contents
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
