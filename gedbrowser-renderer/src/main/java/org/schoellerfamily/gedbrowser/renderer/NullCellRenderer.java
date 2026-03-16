package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders null cell output for display.
 *
 * @author Richard Schoeller
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
