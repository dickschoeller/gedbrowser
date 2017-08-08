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
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmissionLinkListItemRenderer(
            final SubmissionLinkRenderer renderer) {
        this.submissionLinkRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        renderListItemContents(builder);
        return builder;
    }

    /**
     * @param builder the string builder that we will be appending to.
     */
    private void renderListItemContents(final StringBuilder builder) {
        builder.append("<span class=\"label\">Submission:</span> ");
        builder.append(submissionLinkRenderer.getNameHtml());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        renderListItemContents(builder);
        return builder.toString();
    }
}
