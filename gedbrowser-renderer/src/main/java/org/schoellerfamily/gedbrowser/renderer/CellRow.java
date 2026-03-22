package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents cell row.
 *
 * @author Richard Schoeller
 */
public final class CellRow {
    /** */
    private final List<CellRenderer> renderers;

    /**
     * Creates a new CellRow.
     *
     * @param columns the columns
     */
    public CellRow(final int columns) {
        renderers = new ArrayList<>(columns);
        for (int i = 0; i < columns; i++) {
            renderers.add(createNullCellRenderer());
        }
    }

    private NullCellRenderer createNullCellRenderer() {
        return new NullCellRenderer();
    }

    /**
     * Gets the cells.
     *
     * @return the cells
     */
    public CellRenderer[] getCells() {
        return renderers.toArray(new CellRenderer[0]);
    }

    /**
     * Executes set.
     *
     * @param index the zero-based index
     * @param element the element
     */
    public void set(final int index, final CellRenderer element) {
        renderers.set(index, element);
    }

    /**
     * Gets the value.
     *
     * @param index the zero-based index
     * @return the value
     */
    public CellRenderer get(final int index) {
        return renderers.get(index);
    }
}
