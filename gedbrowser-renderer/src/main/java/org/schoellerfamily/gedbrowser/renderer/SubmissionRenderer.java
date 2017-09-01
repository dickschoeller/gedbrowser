package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Submission;

/**
 * Render a Submission.
 *
 * @author Dick Schoeller
 */
public final class SubmissionRenderer extends GedRenderer<Submission>
        implements AttributesRenderer<Submission>,
        HeaderHrefRenderer<Submission>,
        IndexHrefRenderer<Submission>,
        SourcesHrefRenderer<Submission>,
        SubmittersHrefRenderer<Submission> {
    /**
     * @param gedObject the Submission that we are going to render
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public SubmissionRenderer(final Submission gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setNameHtmlRenderer(new NullNameHtmlRenderer());
        setNameIndexRenderer(new NullNameIndexRenderer());
    }

    /**
     * @return the ID string of the person.
     */
    public String getIdString() {
        return getGedObject().getString();
    }

    /**
     * @return a nicely string string for the title
     */
    public String getTitleString() {
        final String nameString = getGedObject().getString();
        return nameString.replace("/", " ").replace("  ", " ").trim();
    }
}
