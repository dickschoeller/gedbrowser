package org.schoellerfamily.gedbrowser.renderer;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;



/**
 * Renders node cell output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class NodeCellRenderer implements CellRenderer {
    /** */
    private final PersonRenderer personRenderer;

    /**
     * Gets the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        String nameHtml = personRenderer.getNameHtml();
        if (StringUtils.isBlank(nameHtml)) {
            nameHtml = "&nbsp;&nbsp;&nbsp;";
        }
        return "<table class=\"bbox\"><tr><td class=\"tree bbox\">%s</td></tr></table>"
            .formatted(nameHtml);
    }

    /**
     * Gets the cell class.
     *
     * @return the cell class
     */
    @Override
    public String getCellClass() {
        return "";
    }
}
