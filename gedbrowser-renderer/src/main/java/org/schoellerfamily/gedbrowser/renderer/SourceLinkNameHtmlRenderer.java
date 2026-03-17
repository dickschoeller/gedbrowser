package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Renders source link name html output for display.
 *
 * @author Richard Schoeller
 */
public class SourceLinkNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the SourceLinkRenderer that is using this helper.
     */
    private final transient SourceLinkRenderer sourceLinkRenderer;

    /**
     * Creates a new SourceLinkNameHtmlRenderer.
     *
     * @param sourceLinkRenderer the source link renderer
     */
    protected SourceLinkNameHtmlRenderer(
            final SourceLinkRenderer sourceLinkRenderer) {
        this.sourceLinkRenderer = sourceLinkRenderer;
    }

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        final SourceLink sourceLink = sourceLinkRenderer
                .getGedObject();
        if (!sourceLink.isSet()) {
            return "";
        }
        final Source source =
                (Source) sourceLink.find(sourceLink.getToString());
        if (source == null) {
            // Necessary because header can have embedded source
            return sourceLink.getToString();
        }
        final SourceRenderer sourceRenderer =
                (SourceRenderer) new GedRendererFactory().create(source,
                        sourceLinkRenderer.getRenderingContext());
        return sourceRenderer.getIndexNameHtml();
    }
}
