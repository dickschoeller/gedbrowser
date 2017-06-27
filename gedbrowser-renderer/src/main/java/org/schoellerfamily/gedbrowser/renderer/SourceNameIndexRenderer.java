package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * @author Dick Schoeller
 *
 */
public class SourceNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient SourceRenderer sourceRenderer;

    /**
     * Constructor.
     *
     * @param sourceRenderer the associated SourceRenderer
     */
    public SourceNameIndexRenderer(final SourceRenderer sourceRenderer) {
        this.sourceRenderer = sourceRenderer;
    }

    /**
     * {@inheritDoc}
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
