package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Render an Family.
 *
 * @author Dick Schoeller
 */
public final class FamilyRenderer extends GedRenderer<Family> {
    /** */
    private static final int INDENT_INCREMENT = 2;

    /**
     * @param gedObject the Family that we are going to render.
     * @param rendererFactory the factory that creates the renderers for the
     *        attributes
     * @param renderingContext the context that we are rendering in
     */
    public FamilyRenderer(final Family gedObject,
            final GedRendererFactory rendererFactory,
            final RenderingContext renderingContext) {
        super(gedObject, rendererFactory, renderingContext);
        setSectionRenderer(new FamilySectionRenderer(this));
    }

    /**
     * @param personRenderer the person renderer whose page we are on
     * @return the spouses renderer.
     */
    public PersonRenderer getSpouse(
            final PersonRenderer personRenderer) {
        final Family family = getGedObject();
        final Person person = personRenderer.getGedObject();
        final Person spouse = family.getSpouse(person);
        return (PersonRenderer) createGedRenderer(spouse);
    }

    /**
     * Return the list of renderers that can be rendered in a list format.
     *
     * @return the list of attribute renderers.
     */
    public List<GedRenderer<?>> getAttributes() {
        final Family family = getGedObject();
        final List<GedRenderer<?>> rendererList =
                new ArrayList<GedRenderer<?>>();
        for (final GedObject attribute : family.getAttributes()) {
            final GedRenderer<?> attributeRenderer =
                    createGedRenderer(attribute);
            if (!attributeRenderer.getListItemContents().isEmpty()) {
                rendererList.add(attributeRenderer);
            }
        }
        return rendererList;
    }

    /**
     * @param builder
     *            Buffer for holding the rendition
     * @param pageRenderer
     *            The page renderer that is invoking this
     * @param sectionNumber
     *            Which family section is being rendered
     */
    void renderSpouses(final StringBuilder builder,
            final GedRenderer<?> pageRenderer, final int sectionNumber) {
        final Family family = getGedObject();
        final GedObject gedObject = pageRenderer.getGedObject();
        if (gedObject instanceof Person) {
            final Person person = (Person) gedObject;
            final GedObject spouse = family.getSpouse(person);
            if (spouse != null) {
                renderPad(builder, 0, true);
                builder.append("  <hr class=\"family\"/>");

                renderPad(builder, 0, true);
                builder.append("  <h3 class=\"family\">Family ")
                        .append(sectionNumber).append("</h3>");

                renderPad(builder, 0, true);
                builder.append("  <p class=\"spouse\">");

                renderPad(builder, 0, true);
                builder.append(
                        "    <span class=\"spouse label\">Spouse:</span> ");
                final String nameHtml = createGedRenderer(spouse).getNameHtml();
                builder.append(nameHtml);

                renderPad(builder, 2, true);
                builder.append("</p>");
                renderNewLine(builder, true);
            }
        } else {
            final List<Person> spouses = family.getSpouses();
            if (!spouses.isEmpty()) {
                renderPad(builder, 0, true);
                builder.append("  <hr class=\"family\"/>");

                renderPad(builder, 0, true);
                builder.append("  <h3 class=\"family\">Family ")
                        .append(sectionNumber).append("</h3>");
            }
            for (final Person spouse : spouses) {
                renderPad(builder, 0, true);
                builder.append("  <p class=\"spouse\">");

                renderPad(builder, 0, true);
                builder.append(
                        "    <span class=\"spouse label\">Spouse:</span> ");
                final String nameHtml = createGedRenderer(spouse).getNameHtml();
                builder.append(nameHtml);

                renderPad(builder, 2, true);
                builder.append("</p>");
                renderNewLine(builder, true);
            }
        }
    }

    /**
     * @return the list of renderers for the children in this family.
     */
    public List<PersonRenderer> getChildren() {
        final Family family = getGedObject();
        final List<Person> children = family.getChildren();
        final List<PersonRenderer> rendererList =
                new ArrayList<PersonRenderer>(children.size());
        for (final Person child : children) {
            final PersonRenderer personRenderer =
                    (PersonRenderer) createGedRenderer(child);
            rendererList.add(personRenderer);
        }
        return rendererList;
    }
    /**
     * Get the standard amount of indent for this construct.
     *
     * @return the increment.
     */
    protected int getIndentIncrement() {
        return INDENT_INCREMENT;
    }
}
