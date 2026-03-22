package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders simple name list item output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class SimpleNameListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SimpleNameRenderer that is using this helper.
     */
    private final SimpleNameRenderer simpleNameRenderer;

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
        if (pad > 0) {
            return builder;
        }

        GedRenderer.renderNewLine(builder, newLine);

        builder.append(simpleNameRenderer.renderAsPhrase());
        return builder;
    }

    /**
     * Returns the list item contents.
     *
     * @return the list item contents
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<span class=\"label\">Name:</span> ");
        builder.append(simpleNameRenderer.renderAsPhrase());
        return builder.toString();
    }
}
