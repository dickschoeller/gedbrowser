package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders cell output for display.
 *
 * @author Richard Schoeller
 */
public interface CellRenderer {
    /**
     * @return string that goes in the cell.
     */
    String getNameHtml();

    /**
     * @return cell class.
     */
    String getCellClass();
}
