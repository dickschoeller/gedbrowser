package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Renders head output for display.
 *
 * @author Richard Schoeller
 */
public final class HeadRenderer extends GedRenderer<Head>
        implements AttributesRenderer<Head>, HeaderHrefRenderer<Head>,
        IndexHrefRenderer<Head>, PlacesHrefRenderer<Head>,
        SaveHrefRenderer<Head>, SourcesHrefRenderer<Head>,
        SubmittersHrefRenderer<Head> {
    /**
     * Creates a new HeadRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public HeadRenderer(final Head gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
    }
}
