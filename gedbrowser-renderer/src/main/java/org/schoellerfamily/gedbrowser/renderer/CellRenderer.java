package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
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
