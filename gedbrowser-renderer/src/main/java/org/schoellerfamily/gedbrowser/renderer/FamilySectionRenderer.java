package org.schoellerfamily.gedbrowser.renderer;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class FamilySectionRenderer implements SectionRenderer {
    /**
     * Holder for the FamilyRenderer that is using this helper.
     */
    private final transient FamilyRenderer familyRenderer;

    /**
     * Constructor.
     *
     * @param familyRenderer the renderer that this is associated with.
     */
    protected FamilySectionRenderer(final FamilyRenderer familyRenderer) {
        this.familyRenderer = familyRenderer;
    }

    @Override
    public final StringBuilder renderAsSection(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final boolean newLine,
            final int pad, final int sectionNumber) {
        final Family family = familyRenderer.getGedObject();
        GedRenderer.renderPad(builder, 0, true);
        builder.append("<div class=\"family\">");

        familyRenderer.renderSpouses(builder, pageRenderer, sectionNumber);

        GedRenderer.renderPad(builder, familyRenderer.getIndentIncrement(),
                true);
        builder.append("<ul>\n");

        for (final GedObject attribute : family.getAttributes()) {
            final GedRenderer<?> gedRenderer = familyRenderer
                    .createGedRenderer(attribute);
            gedRenderer.renderAsListItem(builder, true,
                    familyRenderer.getIndentIncrement() * 2);
        }

        GedRenderer.renderPad(builder, familyRenderer.getIndentIncrement(),
                true);
        builder.append("</ul>\n");

        final List<Person> children = family.getChildren();

        if (!children.isEmpty()) {
            GedRenderer.renderPad(builder,
                    familyRenderer.getIndentIncrement(), true);
            builder.append("<span class=\"children label\">Children:</span>");
            GedRenderer.renderPad(builder,
                    familyRenderer.getIndentIncrement(), true);
            builder.append("<ol class=\"children\">");
        }
        for (final GedObject child : children) {
            GedRenderer.renderPad(builder,
                    familyRenderer.getIndentIncrement() * 2, true);
            builder.append("<li>");
            builder.append(familyRenderer.createGedRenderer(child)
                    .getNameHtml());
            builder.append("</li>");
        }
        if (!children.isEmpty()) {
            GedRenderer.renderPad(builder,
                    familyRenderer.getIndentIncrement(), true);
            builder.append("</ol>");
        }

        GedRenderer.renderPad(builder, 0, true);
        builder.append("</div>");
        GedRenderer.renderNewLine(builder, true);
        return builder;
    }
}
