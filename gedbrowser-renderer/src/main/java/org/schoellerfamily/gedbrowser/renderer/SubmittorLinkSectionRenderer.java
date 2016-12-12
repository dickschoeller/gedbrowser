package org.schoellerfamily.gedbrowser.renderer;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkSectionRenderer implements SectionRenderer {
    /**
     * Holder for the SubmittorLinkRenderer that is using this helper.
     */
    private final transient SubmittorLinkRenderer slRenderer;

    /**
     * Constructor.
     *
     * @param renderer the renderer that this is associated with.
     */
    protected SubmittorLinkSectionRenderer(
            final SubmittorLinkRenderer renderer) {
        this.slRenderer = renderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        builder.append("<p>\nSubmitted by: ");
        builder.append(slRenderer.renderAsPhrase());
        builder.append("</p>\n");
        return builder;
    }
}
