package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dick Schoeller
 */
public final class CellRow {
    /** */
    private final transient List<CellRenderer> renderers;

    /**
     * Constructor.
     *
     * @param columns the number of columns in a row.
     */
    public CellRow(final int columns) {
        renderers = new ArrayList<CellRenderer>(columns);
        for (int i = 0; i < columns; i++) {
            renderers.add(createNullCellRenderer());
        }
    }

    /**
     * Factory method.
     *
     * @return the cell renderer
     */
    private NullCellRenderer createNullCellRenderer() {
        return new NullCellRenderer();
    }

    /**
     * @return the contained array.
     */
    public CellRenderer[] getCells() {
        return renderers.toArray(new CellRenderer[0]);
    }

    /**
     * @param index which cell to set.
     * @param element new value.
     */
    public void set(final int index, final CellRenderer element) {
        renderers.set(index, element);
    }

    /**
     * @param index which cell to get.
     * @return the renderer for the cell.
     */
    public CellRenderer get(final int index) {
        return renderers.get(index);
    }
}
