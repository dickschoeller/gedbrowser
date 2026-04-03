package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Renders source link output for display.
 *
 * @author Richard Schoeller
 */
public final class SourceLinkRenderer extends AbstractLinkRenderer<SourceLink> {
    /**
     * Creates a new SourceLinkRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
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
