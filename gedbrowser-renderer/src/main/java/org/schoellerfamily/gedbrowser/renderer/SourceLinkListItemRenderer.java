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

    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        builder.append(sourceLinkRenderer.renderAsPhrase());
        return builder;
    }

    @Override
    public final String getListItemContents() {
        return sourceLinkRenderer.renderAsPhrase();
    }
}
