package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders line cell output for display.
 *
 * @author Richard Schoeller
 */
public final class LineCellRenderer implements CellRenderer {
    /** */
    private final transient String classString;

    /**
     * Executes line cell renderer.
     *
     * @param classString the class string
     */
    public LineCellRenderer(final String classString) {
        this.classString = classString;
    }

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
        return classString;
    }
}
