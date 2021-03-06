package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Date;

/**
 * Render a Date.
 *
 * @author Dick Schoeller
 */
public final class DateRenderer extends GedRenderer<Date> {
    /**
     * @param gedObject the Date that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public DateRenderer(final Date gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new DateListItemRenderer(this));
        setPhraseRenderer(new DatePhraseRenderer(this));
    }
}
