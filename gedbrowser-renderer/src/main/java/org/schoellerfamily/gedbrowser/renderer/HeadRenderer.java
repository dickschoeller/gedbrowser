package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * Render a Head.
 *
 * @author Dick Schoeller
 */
public final class HeadRenderer extends GedRenderer<Head>
        implements AttributesRenderer<Head>, HeaderHrefRenderer<Head>,
        IndexHrefRenderer<Head>, PlacesHrefRenderer<Head>,
        SourcesHrefRenderer<Head>, SubmittersHrefRenderer<Head> {
    /**
     * @param gedObject the Head that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public HeadRenderer(final Head gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
