package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * Render a Head.
 *
 * @author Dick Schoeller
 */
public final class HeadRenderer extends GedRenderer<Head> {
    /**
     * @param gedObject the Head that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider calendar provider
     */
    public HeadRenderer(final Head gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setSectionRenderer(new HeadSectionRenderer(this));
    }
}
