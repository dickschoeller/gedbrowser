package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * Render a NoteLink.
 *
 * @author Dick Schoeller
 */
public final class NoteLinkRenderer extends AbstractLinkRenderer<NoteLink> {
    /**
     * @param gedObject the NoteLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
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
