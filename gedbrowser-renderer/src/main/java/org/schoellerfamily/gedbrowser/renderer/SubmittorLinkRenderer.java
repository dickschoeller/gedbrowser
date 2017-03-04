package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
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
     * @param provider the calendar provider
     */
    public SubmittorLinkRenderer(final SubmittorLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setListItemRenderer(new SubmittorLinkListItemRenderer(this));
        setPhraseRenderer(new SubmittorLinkPhraseRenderer(this));
    }

    /**
     * Build the name string.
     *
     * @param toLink
     *            The Submittor that the link points to
     * @return The name of the linked submittor
     */
    String getNameString(final Submittor toLink) {
        final SubmittorLink submittorLink = getGedObject();
        if (toLink == null || toLink.getName() == null
                || !toLink.getName().isSet()) {
            return GedRenderer.escapeString(submittorLink.getToString());
        }
        return GedRenderer.escapeString(toLink.getName().getString());
    }
}
