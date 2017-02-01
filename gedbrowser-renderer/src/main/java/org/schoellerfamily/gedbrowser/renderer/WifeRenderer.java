package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Render a Wife.
 *
 * @author Dick Schoeller
 */
public final class WifeRenderer extends AbstractLinkRenderer<Wife> {
    /**
     * @param gedObject the Wife that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public WifeRenderer(final Wife gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
    }
}
