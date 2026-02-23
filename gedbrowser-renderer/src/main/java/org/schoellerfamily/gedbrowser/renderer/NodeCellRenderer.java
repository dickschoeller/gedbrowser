package org.schoellerfamily.gedbrowser.renderer;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Cell for a person in the table that represents the ancestor
 * tree.
 *
 * @author Dick Schoeller
 */
@RequiredArgsConstructor
@Getter
public final class NodeCellRenderer implements CellRenderer {
    /** */
    private final transient PersonRenderer personRenderer;

    @Override
    public String getNameHtml() {
        String nameHtml = personRenderer.getNameHtml();
        if (StringUtils.isBlank(nameHtml)) {
            nameHtml = "&nbsp;&nbsp;&nbsp;";
        }
        return "<table class=\"bbox\"><tr><td class=\"tree bbox\">"
                + nameHtml + "</td></tr></table>";
    }

    @Override
    public String getCellClass() {
        return "";
    }
}
