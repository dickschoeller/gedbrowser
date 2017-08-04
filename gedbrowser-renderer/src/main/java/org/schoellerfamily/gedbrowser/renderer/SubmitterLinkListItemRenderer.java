package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmitterLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SubmitterLinkRenderer that is using this helper.
     */
    private final transient SubmitterLinkRenderer submitterLinkRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmitterLinkListItemRenderer(
            final SubmitterLinkRenderer renderer) {
        this.submitterLinkRenderer = renderer;
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
        builder.append("<span class=\"label\">Submitter:</span> ");
        builder.append(submitterLinkRenderer.getNameHtml());
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
