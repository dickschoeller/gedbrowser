package org.schoellerfamily.gedbrowser.renderer;

/**
 * Render an empty location in the table that is used for
 * the ancestor tree.
 *
 * @author Dick Schoeller
 */
public final class NullCellRenderer implements CellRenderer {
    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        return "&nbsp;&nbsp;&nbsp;";
    }

    /**
     * Returns the cell class.
     *
     * @return the cell class
     */
    @Override
    public String getCellClass() {
        return "";
    }
}
