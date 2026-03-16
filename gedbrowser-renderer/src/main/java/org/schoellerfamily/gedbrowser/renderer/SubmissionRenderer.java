package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;

/**
 * Render a Submission.
 *
 * @author Dick Schoeller
 */
public final class SubmissionRenderer extends GedRenderer<Submission>
        implements AttributesRenderer<Submission>,
        HeaderHrefRenderer<Submission>,
        IndexHrefRenderer<Submission>,
        SaveHrefRenderer<Submission>,
        SourcesHrefRenderer<Submission>,
        SubmittersHrefRenderer<Submission> {
    /**
     * Creates a new SubmissionRenderer.
     *
     * @param gedObject the ged object
     * @param rendererFactory the renderer factory
     * @param renderingContext the rendering context
     */
    public SubmissionRenderer(final Submission gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new NullNameHtmlRenderer());
        setNameIndexRenderer(new NullNameIndexRenderer());
    }

    /**
     * Gets the id string.
     *
     * @return the id string
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * Gets the title string.
     *
     * @return the title string
     */
    public String getTitleString() {
        final String nameString = getGedObject().getString();
        return nameString.replace("/", " ").replace("  ", " ").trim();
    }
}
