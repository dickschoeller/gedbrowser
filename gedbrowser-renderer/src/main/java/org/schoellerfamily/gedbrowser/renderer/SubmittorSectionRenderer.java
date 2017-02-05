package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;

/**
 * @author Dick Schoeller
 */
public final class SubmittorSectionRenderer implements SectionRenderer {
    /**
     * Holder for the SubmittorRenderer that is using this helper.
     */
    private final transient SubmittorRenderer submittorRenderer;

    /**
     * Constructor.
     *
     * @param submittorRenderer the renderer that this is associated with.
     */
    public SubmittorSectionRenderer(final SubmittorRenderer submittorRenderer) {
        this.submittorRenderer = submittorRenderer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Submittor submittor = submittorRenderer.getGedObject();
        // FIXME should have a submittor phrase renderer
        // to put the name up encoded.
        final Name name = submittor.getName();
        String namestring;
        if (name == null) {
            namestring = "";
        } else {
            namestring = GedRenderer.escapeString(name.getString());
        }
        // FIXME should have ": " after submittor.
        final String title = "Submittor" + namestring;

        GedRenderer.renderPad(builder, pad, true);
        builder.append("<h2 class=\"name\">").append(title).append("</h2>");

        submittorRenderer.renderAttributeList(builder, pad, submittor);

        return builder;
    }
}
