package org.schoellerfamily.gedbrowser.renderer;

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
     */
    public NameRenderer(final Name gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new NameNameHtmlRenderer(this));
        setListItemRenderer(new NameListItemRenderer(this));
        setPhraseRenderer(new NamePhraseRenderer(this));
        setNameIndexRenderer(new NameNameIndexRenderer(this));
    }
}
