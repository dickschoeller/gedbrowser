package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * @author Dick Schoeller
 */
public final class AttributeSectionRenderer implements SectionRenderer {
    /**
     * Holder for the AttributeRenderer that is using this helper.
     */
    private final transient AttributeRenderer attributeRenderer;

    /**
     * Constructor.
     *
     * @param attributeRenderer the renderer that this is associated with.
     */
    protected AttributeSectionRenderer(
            final AttributeRenderer attributeRenderer) {
        this.attributeRenderer = attributeRenderer;
    }

    @Override
    public StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> outerRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Attribute attribute = attributeRenderer.getGedObject();

        builder.append("<p><span class=\"label\">");
        builder.append(attribute.getString());
        builder.append(":</span> ");
        builder.append(attribute.getTail());

        builder.append("</p>\n<ul>\n");

        for (final GedObject subAttr : attribute.getAttributes()) {
            final GedRenderer<? extends GedObject> renderer =
                    attributeRenderer.createGedRenderer(subAttr);
            renderer.renderAsListItem(builder, newLine, pad);
        }

        builder.append("</ul>\n");

        return builder;
    }
}
