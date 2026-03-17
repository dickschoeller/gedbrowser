package org.schoellerfamily.gedbrowser.renderer;

/**
 * Renders submission link name html output for display.
 *
 * @author Richard Schoeller
 */
public class SubmissionLinkNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final SubmissionLinkRenderer submissionLinkRenderer;
    /**
     * Creates a new SubmissionLinkNameHtmlRenderer.
     *
     * @param submissionLinkRenderer the submission link renderer
     */
    public SubmissionLinkNameHtmlRenderer(
            final SubmissionLinkRenderer submissionLinkRenderer) {
        this.submissionLinkRenderer = submissionLinkRenderer;
    }

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public String getNameHtml() {
        final String name =
                submissionLinkRenderer.getGedObject().getToString();
        final String dbname =
                submissionLinkRenderer.getGedObject().getDbName();
        return "<a class=\"name\" "
        + "href=\"submission?db=" + dbname + "&amp;id=" + name + "\">"
        + name + "</a>";
    }
}
