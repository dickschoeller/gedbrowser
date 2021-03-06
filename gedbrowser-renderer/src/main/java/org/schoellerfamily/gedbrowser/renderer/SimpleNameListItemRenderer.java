package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SimpleNameListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SimpleNameRenderer that is using this helper.
     */
    private final transient SimpleNameRenderer simpleNameRenderer;

    /**
     * Constructor.
     *
     * @param nameRenderer the renderer that this is associated with.
     */
    protected SimpleNameListItemRenderer(
            final SimpleNameRenderer nameRenderer) {
        this.simpleNameRenderer = nameRenderer;
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

        builder.append(simpleNameRenderer.renderAsPhrase());
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        builder.append("<span class=\"label\">Name:</span> ");
        builder.append(simpleNameRenderer.renderAsPhrase());
        return builder.toString();
    }
}
