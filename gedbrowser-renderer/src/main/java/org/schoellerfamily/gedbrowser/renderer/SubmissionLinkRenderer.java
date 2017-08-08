package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;

/**
 * Render a SubmissionLink.
 *
 * @author Dick Schoeller
 */
public final class SubmissionLinkRenderer extends
        AbstractLinkRenderer<SubmissionLink> {
    /**
     * @param gedObject the SubmissionLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SubmissionLinkRenderer(final SubmissionLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new SubmissionLinkListItemRenderer(this));
        setPhraseRenderer(new SubmissionLinkPhraseRenderer(this));
        setNameHtmlRenderer(new SubmissionLinkNameHtmlRenderer(this));
    }

    /**
     * Build the name string.
     *
     * @return The name of the linked submission
     */
    String getNameString() {
        return this.getNameHtml();
    }
}
