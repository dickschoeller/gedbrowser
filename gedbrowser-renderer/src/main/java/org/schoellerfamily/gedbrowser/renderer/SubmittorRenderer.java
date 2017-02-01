package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * Render a Submittor.
 *
 * @author Dick Schoeller
 */
public final class SubmittorRenderer extends GedRenderer<Submittor> {
    /**
     * @param gedObject the Submittor that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public SubmittorRenderer(final Submittor gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setSectionRenderer(new SubmittorSectionRenderer(this));
    }
}
