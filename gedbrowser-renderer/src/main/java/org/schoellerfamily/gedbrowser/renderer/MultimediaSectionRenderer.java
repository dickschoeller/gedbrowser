package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;

/**
 * @author Dick schoeller
 */
public final class MultimediaSectionRenderer implements SectionRenderer {
    /**
     * Holder for the MultimediaRenderer that is using this helper.
     */
    private final transient MultimediaRenderer multimediaRenderer;

    /**
     * Constructor.
     *
     * @param multimediaRenderer the renderer that this is associated with.
     */
    protected MultimediaSectionRenderer(
            final MultimediaRenderer multimediaRenderer) {
        this.multimediaRenderer = multimediaRenderer;
    }


    @Override
    public StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> outerRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Multimedia attribute = multimediaRenderer.getGedObject();

        builder.append("<p><span class=\"label\">");
        builder.append(attribute.getString());
        builder.append(":</span> ");
        builder.append(attribute.getTail());

        builder.append("</p>\n<ul>\n");

        for (final GedObject subAttr : attribute.getAttributes()) {
            final GedRenderer<? extends GedObject> renderer =
                    multimediaRenderer.createGedRenderer(subAttr);
            renderer.renderAsListItem(builder, newLine, pad);
        }

        builder.append("</ul>\n");

        return builder;
    }
}
