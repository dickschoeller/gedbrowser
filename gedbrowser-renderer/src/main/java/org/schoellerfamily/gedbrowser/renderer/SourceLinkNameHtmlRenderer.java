package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * @author Dick Schoeller
 */
public class SourceLinkNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the SourceLinkRenderer that is using this helper.
     */
    private final transient SourceLinkRenderer sourceLinkRenderer;

    /**
     * Constructor.
     *
     * @param sourceLinkRenderer the renderer that is using this helper.
     */
    protected SourceLinkNameHtmlRenderer(
            final SourceLinkRenderer sourceLinkRenderer) {
        this.sourceLinkRenderer = sourceLinkRenderer;
    }

    /**
     * {@inheritDoc}
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
