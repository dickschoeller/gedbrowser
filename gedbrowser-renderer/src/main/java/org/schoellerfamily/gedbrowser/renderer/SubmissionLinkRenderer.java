package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;

/**
 * Renders submission link output for display.
 *
 * @author Richard Schoeller
 */
public final class SubmissionLinkRenderer extends
        AbstractLinkRenderer<SubmissionLink> {
    /**
     * Creates a new SubmissionLinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
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
    public String getNameString() {
        return this.getNameHtml();
    }
}
