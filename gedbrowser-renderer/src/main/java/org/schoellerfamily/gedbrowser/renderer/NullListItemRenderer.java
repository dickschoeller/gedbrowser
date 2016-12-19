package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class NullListItemRenderer implements ListItemRenderer {
    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        return builder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getListItemContents() {
        return "";
    }
}
