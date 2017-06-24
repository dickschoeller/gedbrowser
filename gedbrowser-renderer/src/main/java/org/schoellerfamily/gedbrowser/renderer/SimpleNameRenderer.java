package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Render a Name.
 *
 * @author Dick Schoeller
 */
public final class SimpleNameRenderer extends GedRenderer<Name> {
    /**
     * @param gedObject the Name that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SimpleNameRenderer(final Name gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new SimpleNameNameHtmlRenderer(this));
        setListItemRenderer(new SimpleNameListItemRenderer(this));
        setPhraseRenderer(new SimpleNamePhraseRenderer(this));
        setNameIndexRenderer(new SimpleNameNameIndexRenderer(this));
    }
}
