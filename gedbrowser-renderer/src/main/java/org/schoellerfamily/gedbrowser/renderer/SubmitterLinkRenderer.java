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
     * Creates a new SubmitterLinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
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
    public String getNameString() {
        return this.getNameHtml();
    }
}
