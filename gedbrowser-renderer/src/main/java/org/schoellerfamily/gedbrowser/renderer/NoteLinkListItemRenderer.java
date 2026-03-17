package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders note link list item output for display.
 *
 * @author Richard Schoeller
 */
public class NoteLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the NoteLinkRenderer that is using this helper.
     */
    private final transient NoteLinkRenderer noteLinkRenderer;

    /**
     * Creates a new NoteLinkListItemRenderer.
     *
     * @param renderer the renderer
     */
    protected NoteLinkListItemRenderer(
            final NoteLinkRenderer renderer) {
        this.noteLinkRenderer = renderer;
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
        renderListItemContents(builder);
        return builder;
    }

    private void renderListItemContents(final StringBuilder builder) {
        builder.append("<span class=\"label\">Note:</span> ");
        builder.append(noteLinkRenderer.getIndexName());
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
