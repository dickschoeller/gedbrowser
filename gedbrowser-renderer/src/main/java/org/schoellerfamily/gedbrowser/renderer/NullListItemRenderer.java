package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class NullListItemRenderer implements ListItemRenderer {
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        return builder;
    }

    @Override
    public final String getListItemContents() {
        return "";
    }
}
