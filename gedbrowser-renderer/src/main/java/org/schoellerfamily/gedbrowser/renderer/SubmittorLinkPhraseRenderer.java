package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkPhraseRenderer implements PhraseRenderer {
    /**
     * Holder for the SubmittorLinkRenderer that is using this helper.
     */
    private final transient SubmittorLinkRenderer slRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmittorLinkPhraseRenderer(
            final SubmittorLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String renderAsPhrase() {
        final SubmittorLink submittorLink = slRenderer.getGedObject();
        final Submittor toLink = (Submittor) submittorLink.find(submittorLink
                .getToString());
        final String namestring = slRenderer.getNameString(toLink);

        // FIXME this will have to change
        // to match the way that URLs are formed here.
        final StringBuilder builder = new StringBuilder(60);
        builder.append("<a class=\"name\" href=\"source?db=");
        builder.append(submittorLink.getDbName());
        builder.append("&amp;id=");
        builder.append(submittorLink.getToString());
        builder.append("\">");
        builder.append(namestring);
        builder.append("</a>");
        return builder.toString();
    }
}
