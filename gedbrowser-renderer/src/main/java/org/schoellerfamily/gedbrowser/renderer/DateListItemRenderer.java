package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public final class DateListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the DateRenderer that is using this helper.
     */
    private final transient DateRenderer dateRenderer;

    /**
     * Constructor.
     *
     * @param dateRenderer the renderer that this is associated with.
     */
    protected DateListItemRenderer(final DateRenderer dateRenderer) {
        this.dateRenderer = dateRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        GedRenderer.renderPad(builder, pad, newLine);
        builder.append(getListItemContents());
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getListItemContents() {
        return dateRenderer.renderAsPhrase();
    }
}
