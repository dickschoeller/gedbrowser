package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders submitter link list item output for display.
 *
 * @author Richard Schoeller
 */
public class SubmitterLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final transient SubmitterLinkRenderer submitterLinkRenderer;

    /**
     * Creates a new SubmitterLinkListItemRenderer.
     *
     * @param renderer the renderer
     */
    protected SubmitterLinkListItemRenderer(
            final SubmitterLinkRenderer renderer) {
        this.submitterLinkRenderer = renderer;
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
        builder.append("<span class=\"label\">Submitter:</span> ");
        builder.append(submitterLinkRenderer.getNameHtml());
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
