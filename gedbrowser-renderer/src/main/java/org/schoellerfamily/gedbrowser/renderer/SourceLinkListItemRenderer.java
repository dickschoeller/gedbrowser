package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders source link list item output for display.
 *
 * @author Richard Schoeller
 */
public class SourceLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SourceLinkRenderer that is using this helper.
     */
    private final transient SourceLinkRenderer sourceLinkRenderer;

    /**
     * Creates a new SourceLinkListItemRenderer.
     *
     * @param renderer the renderer
     */
    protected SourceLinkListItemRenderer(
            final SourceLinkRenderer renderer) {
        this.sourceLinkRenderer = renderer;
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
        builder.append("<span class=\"label\">Source:</span> ");
        builder.append(sourceLinkRenderer.getNameHtml());
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
