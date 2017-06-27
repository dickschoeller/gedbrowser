package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SourceLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SourceLinkRenderer that is using this helper.
     */
    private final transient SourceLinkRenderer sourceLinkRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SourceLinkListItemRenderer(
            final SourceLinkRenderer renderer) {
        this.sourceLinkRenderer = renderer;
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
        builder.append("<span class=\"label\">Source:</span> ");
        builder.append(sourceLinkRenderer.getNameHtml());
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
