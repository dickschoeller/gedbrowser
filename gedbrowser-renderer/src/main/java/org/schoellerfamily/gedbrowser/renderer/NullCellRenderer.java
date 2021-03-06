package org.schoellerfamily.gedbrowser.renderer;

/**
 * Render an empty location in the table that is used for
 * the ancestor tree.
 *
 * @author Dick Schoeller
 */
public final class NullCellRenderer implements CellRenderer {
    /**
     * {@inheritDoc}
     */
    @Override
    public String getNameHtml() {
        return "&nbsp;&nbsp;&nbsp;";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCellClass() {
        return "";
    }
}
