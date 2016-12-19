package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * @author Dick Schoeller
 */
public class HeadSectionRenderer implements SectionRenderer {
    /**
     * Holder for the HeadRenderer that is using this helper.
     */
    private final transient HeadRenderer headRenderer;

    /**
     * Constructor.
     *
     * @param headRenderer the renderer that this is associated with.
     */
    protected HeadSectionRenderer(final HeadRenderer headRenderer) {
        this.headRenderer = headRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Head head = headRenderer.getGedObject();
        final String title = head.getFilename();

        GedRenderer.renderNewLine(builder, newLine);

        builder.append(headRenderer.getHeaderHtml(title, ""));

        GedRenderer.renderPad(builder, pad, true);
        builder.append("<h2 class=\"name\">").append(title).append("</h2>");

        headRenderer.renderAttributeList(builder, pad, head);

        builder.append(headRenderer.getTrailerHtml("Header"));

        return builder;
    }
}
