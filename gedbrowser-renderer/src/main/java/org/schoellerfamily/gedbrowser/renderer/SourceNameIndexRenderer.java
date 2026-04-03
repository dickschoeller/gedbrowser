package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Source;

import lombok.RequiredArgsConstructor;

/**
 * Renders source name index output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class SourceNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final SourceRenderer sourceRenderer;

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public final String getIndexName() {
        final Source source = sourceRenderer.getGedObject();
        if (!source.isSet()) {
            return "";
        }

        final String nameHtml = sourceRenderer.getTitleString();

        return "<a href=\"source?db=" + source.getDbName() + "&amp;id="
            + source.getString() + "\" class=\"name\" id=\"source-"
            + source.getString() + "\">" + nameHtml + " ("
            + source.getString() + ")</a>";
    }
}
