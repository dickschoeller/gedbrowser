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
     * Constructor.
     *
     * @param nameRenderer the renderer that this is associated with.
     */
    protected NameListItemRenderer(final NameRenderer nameRenderer) {
        this.nameRenderer = nameRenderer;
    }

    /**
     * {@inheritDoc}
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

    /**
     * @param builder the string builder that we will be appending to.
     */
    private void renderListItemContents(final StringBuilder builder) {
        builder.append("<span class=\"label\">Name:</span> ");
        builder.append(nameRenderer.renderAsPhrase());
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
