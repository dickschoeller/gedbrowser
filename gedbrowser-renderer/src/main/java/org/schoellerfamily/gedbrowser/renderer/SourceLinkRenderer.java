package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Render a SourceLink.
 *
 * @author Dick Schoeller
 */
public final class SourceLinkRenderer extends AbstractLinkRenderer<SourceLink> {
    /**
     * @param gedObject the SourceLink that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SourceLinkRenderer(final SourceLink gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setListItemRenderer(new SourceLinkListItemRenderer(this));
        setPhraseRenderer(new SourceLinkPhraseRenderer(this));
        setNameHtmlRenderer(new SourceLinkNameHtmlRenderer(this));
    }
}
