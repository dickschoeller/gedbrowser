package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmissionLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SubmissionLinkRenderer that is using this helper.
     */
    private final transient SubmissionLinkRenderer submissionLinkRenderer;

    /**
     * Creates a new SubmissionLinkListItemRenderer.
     *
     * @param renderer the renderer
     */
    protected SubmissionLinkListItemRenderer(
            final SubmissionLinkRenderer renderer) {
        this.submissionLinkRenderer = renderer;
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
        builder.append("<span class=\"label\">Submission:</span> ");
        builder.append(submissionLinkRenderer.getNameHtml());
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
