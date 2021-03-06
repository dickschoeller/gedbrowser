package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick Schoeller
 */
public final class MultimediaRenderer extends GedRenderer<Multimedia> {
    /**
     * @param gedObject the Multimedia object that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        multimedia objects
     * @param renderingContext the context that we are rendering in
     */
    public MultimediaRenderer(final Multimedia gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new MultimediaListItemRenderer(this));
        setPhraseRenderer(new MultimediaPhraseRenderer(this));
    }

    /**
     * @return the string field processed for HTML special characters.
     */
    public String getEscapedString() {
        final Multimedia attribute = getGedObject();
        return GedRenderer.escapeString(attribute);
    }
}
