package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Render a SourceLink.
 *
 * @author Dick Schoeller
 */
public final class SourceLinkRenderer extends AbstractLinkRenderer<SourceLink> {
    /**
     * @param gedObject the SourceLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public SourceLinkRenderer(final SourceLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setListItemRenderer(new SourceLinkListItemRenderer(this));
        setPhraseRenderer(new SourceLinkPhraseRenderer(this));
    }
}
