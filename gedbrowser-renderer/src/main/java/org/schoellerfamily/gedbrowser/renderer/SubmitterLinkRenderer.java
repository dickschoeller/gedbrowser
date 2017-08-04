package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * Render a SubmitterLink.
 *
 * @author Dick Schoeller
 */
public final class SubmitterLinkRenderer extends
        AbstractLinkRenderer<SubmitterLink> {
    /**
     * @param gedObject the SubmitterLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SubmitterLinkRenderer(final SubmitterLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new SubmitterLinkListItemRenderer(this));
        setPhraseRenderer(new SubmitterLinkPhraseRenderer(this));
        setNameHtmlRenderer(new SubmitterLinkNameHtmlRenderer(this));
    }

    /**
     * Build the name string.
     *
     * @return The name of the linked submitter
     */
    String getNameString() {
        return this.getNameHtml();
    }
}
