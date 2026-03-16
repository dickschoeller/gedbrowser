package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * Renders person name index output for display.
 *
 * @author Richard Schoeller
 */
public class PersonNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient PersonRenderer personRenderer;

    /**
     * Creates a new PersonNameIndexRenderer.
     *
     * @param personRenderer the person renderer
     */
    public PersonNameIndexRenderer(final PersonRenderer personRenderer) {
        this.personRenderer = personRenderer;
    }

    /**
     * Returns the index name.
     *
     * @return the index name
     */
    @Override
    public final String getIndexName() {
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
        final String nameHtml = renderer.getIndexName();
        final GetDateVisitor birthVisitor = new GetDateVisitor("Birth");
        person.accept(birthVisitor);
        final String birthYear = birthVisitor.getYear();
        final GetDateVisitor deathVisitor = new GetDateVisitor("Death");
        person.accept(deathVisitor);
        final String deathYear = deathVisitor.getYear();

        return "<a href=\"person?db=" + person.getDbName() + "&amp;id="
                + person.getString() + "\" class=\"name\">" + nameHtml
                + dateRangeString(birthYear, deathYear) + " ("
                + person.getString() + ")</a>";
    }

    private String dateRangeString(final String birthYear,
            final String deathYear) {
        if (birthYear.isEmpty() && deathYear.isEmpty()) {
            return "";
        }
        return " (" + birthYear + "-" + deathYear + ")";
    }
}
