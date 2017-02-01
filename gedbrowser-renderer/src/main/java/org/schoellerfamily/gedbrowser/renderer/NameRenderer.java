package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Render a Name.
 *
 * @author Dick Schoeller
 */
public final class NameRenderer extends GedRenderer<Name> {
    /**
     * @param gedObject the Name that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider
     */
    public NameRenderer(final Name gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(gedObject, rendererFactory, renderingContext, provider);
        setNameHtmlRenderer(new NameNameHtmlRenderer(this));
        setListItemRenderer(new NameListItemRenderer(this));
        setPhraseRenderer(new NamePhraseRenderer(this));
        setNameIndexRenderer(new NameNameIndexRenderer(this));
    }
}
