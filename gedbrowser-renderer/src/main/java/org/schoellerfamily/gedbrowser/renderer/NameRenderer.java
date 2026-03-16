package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Render a Name.
 *
 * @author Dick Schoeller
 */
public final class NameRenderer extends GedRenderer<Name> {
    /**
     * Creates a new NameRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
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
