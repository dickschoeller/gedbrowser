package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class NameListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the NameRenderer that is using this helper.
     */
    private final transient NameRenderer nameRenderer;

    /**
     * Creates a new NameListItemRenderer.
     *
     * @param nameRenderer the name renderer to use
     */
    protected NameListItemRenderer(final NameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
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
        if (pad > 0) {
            return builder;
        }

        GedRenderer.renderNewLine(builder, newLine);

        builder.append(nameRenderer.renderAsPhrase());
        return builder;
    }

    private void renderListItemContents(final StringBuilder builder) {
        builder.append("<span class=\"label\">Name:</span> ");
        builder.append(nameRenderer.renderAsPhrase());
    }

    /**
     * Returns the list item contents.
     *
     * @return the list item contents
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        renderListItemContents(builder);
        return builder.toString();
    }
}
