package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkListItemRenderer implements ListItemRenderer {
    /**
     * Holder for the SubmittorLinkRenderer that is using this helper.
     */
    private final transient SubmittorLinkRenderer slRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmittorLinkListItemRenderer(
            final SubmittorLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsListItem(final StringBuilder builder,
            final boolean newLine, final int pad) {
        renderListItemContents(builder);
        return builder;
    }

    /**
     * @param builder the string builder that we will be appending to.
     */
    private void renderListItemContents(final StringBuilder builder) {
        final SubmittorLink submittorLink = slRenderer.getGedObject();
        builder.append("<span class=\"label\">Submittor:</span> ");
        builder.append("<a href=\"submittor?db=");
        builder.append(submittorLink.getDbName());
        builder.append("&amp;id=");
        builder.append(submittorLink.getToString());
        builder.append("\">");
        builder.append(submittorLink.getToString());
        builder.append("</a>");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getListItemContents() {
        final StringBuilder builder = new StringBuilder();
        renderListItemContents(builder);
        return builder.toString();
    }
}
