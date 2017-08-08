package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmissionLinkNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final SubmissionLinkRenderer submissionLinkRenderer;
    /**
     * Constructor.
     *
     * @param submissionRender the parent renderer
     */
    public SubmissionLinkNameHtmlRenderer(final SubmissionLinkRenderer submissionLinkRender) {
        this.submissionLinkRenderer = submissionLinkRender;
    }

    /**
     * {@inheritDoc}
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
