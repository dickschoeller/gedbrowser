package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Date;

/**
 * Renders date output for display.
 *
 * @author Richard Schoeller
 */
public final class DateRenderer extends GedRenderer<Date> {
    /**
     * Creates a new DateRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public DateRenderer(final Date gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new DateListItemRenderer(this));
        setPhraseRenderer(new DatePhraseRenderer(this));
    }
}
