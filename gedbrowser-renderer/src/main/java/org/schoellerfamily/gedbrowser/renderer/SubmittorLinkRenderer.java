package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * Render a SubmittorLink.
 *
 * @author Dick Schoeller
 */
public final class SubmittorLinkRenderer extends
        AbstractLinkRenderer<SubmittorLink> {
    /**
     * @param gedObject the SubmittorLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SubmittorLinkRenderer(final SubmittorLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new SubmittorLinkListItemRenderer(this));
        setPhraseRenderer(new SubmittorLinkPhraseRenderer(this));
        setNameHtmlRenderer(new SubmittorLinkNameHtmlRenderer(this));
    }

    /**
     * Build the name string.
     *
     * @return The name of the linked submittor
     */
    String getNameString() {
        return this.getNameHtml();
    }
}
