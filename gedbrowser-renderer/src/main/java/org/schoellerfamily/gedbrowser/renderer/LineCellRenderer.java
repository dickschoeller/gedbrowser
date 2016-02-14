package org.schoellerfamily.gedbrowser.renderer;

/**
 * Render a cell that is empty but has additional class text
 * for the table displays the ancestor tree.  The extra class
 * cells are used for line drawing.
 *
 * @author Dick Schoeller
 */
public final class LineCellRenderer implements CellRenderer {
    /** */
    private final transient String classString;

    /**
     * Constructor.
     *
     * @param classString the classString
     */
    public LineCellRenderer(final String classString) {
        this.classString = classString;
    }

    @Override
    public String getNameHtml() {
        return "&nbsp;&nbsp;&nbsp;";
    }

    @Override
    public String getCellClass() {
        return classString;
    }
}
