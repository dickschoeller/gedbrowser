package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * Renders simple name output for display.
 *
 * @author Richard Schoeller
 */
public final class SimpleNameRenderer extends GedRenderer<Name> {
    /**
     * Creates a new SimpleNameRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
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
