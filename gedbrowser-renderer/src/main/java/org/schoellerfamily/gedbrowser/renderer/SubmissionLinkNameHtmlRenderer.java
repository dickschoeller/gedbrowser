package org.schoellerfamily.gedbrowser.renderer;

import lombok.RequiredArgsConstructor;

/**
 * Renders submission link name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class SubmissionLinkNameHtmlRenderer implements NameHtmlRenderer {
    /** */
    private final SubmissionLinkRenderer submissionLinkRenderer;

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
