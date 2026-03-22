package org.schoellerfamily.gedbrowser.renderer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Renders line cell output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class LineCellRenderer implements CellRenderer {
    /** */
    private final String cellClass;

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        return "&nbsp;&nbsp;&nbsp;";
    }
}
