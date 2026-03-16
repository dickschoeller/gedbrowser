package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * Render a NoteLink.
 *
 * @author Dick Schoeller
 */
public final class NoteLinkRenderer extends AbstractLinkRenderer<NoteLink> {
    /**
     * Creates a new NoteLinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public NoteLinkRenderer(final NoteLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new NoteLinkListItemRenderer(this));
        setPhraseRenderer(new NoteLinkPhraseRenderer(this));
        setNameIndexRenderer(new NoteLinkNameIndexRenderer(this));
    }
}
