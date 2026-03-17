package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * Renders multimedia output for display.
 *
 * @author Richard Schoeller
 */
public final class MultimediaRenderer extends GedRenderer<Multimedia> {
    /**
     * Creates a new MultimediaRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public MultimediaRenderer(final Multimedia gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new MultimediaListItemRenderer(this));
        setPhraseRenderer(new MultimediaPhraseRenderer(this));
    }

    /**
     * Gets the escaped string.
     *
     * @return the escaped string
     */
    public String getEscapedString() {
        final Multimedia attribute = getGedObject();
        return escapeString(attribute);
    }
}
