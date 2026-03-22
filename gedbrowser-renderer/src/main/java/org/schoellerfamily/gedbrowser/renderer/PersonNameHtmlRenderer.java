package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

import lombok.RequiredArgsConstructor;

/**
 * Renders person name html output for display.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class PersonNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the PersonRenderer that is using this helper.
     */
    private final PersonRenderer personRenderer;

    /**
     * Returns the name html.
     *
     * @return the name html
     */
    @Override
    public final String getNameHtml() {
        final Person person = personRenderer.getGedObject();
        if (!person.isSet()) {
            return "";
        }
        if (personRenderer.isConfidential()) {
            return "Confidential";
        }
        if (personRenderer.isHiddenLiving()) {
            return "Living";
        }
        final GedRenderer<? extends GedObject> renderer =
                personRenderer.createGedRenderer(person.getName());
        final String nameHtml = renderer.getNameHtml();

        final String spanString = spanString(person);

        return "<a href=\"person?db="
            + person.getDbName() + "&amp;id=" + person.getString()
            + "\" class=\"name\">" + nameHtml + spanString + " ["
            + person.getString() + "]" + "</a>";
    }

    private String spanString(final Person person) {
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        final String birthYear = birthVisitor.getYear();
        final GetDateVisitor deathVisitor = new GetDateVisitor("Death");
        person.accept(deathVisitor);
        final String deathYear = deathVisitor.getYear();

        if (birthYear.isEmpty() && deathYear.isEmpty()) {
            return "";
        }
        return " (" + birthYear + "-" + deathYear + ")";
    }
}
