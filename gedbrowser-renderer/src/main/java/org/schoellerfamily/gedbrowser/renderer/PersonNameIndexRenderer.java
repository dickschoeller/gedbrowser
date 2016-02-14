package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class PersonNameIndexRenderer implements NameIndexRenderer {
    /** */
    private final transient PersonRenderer personRenderer;

    /**
     * Constructor.
     *
     * @param personRenderer the associated personRenderer
     */
    public PersonNameIndexRenderer(final PersonRenderer personRenderer) {
        this.personRenderer = personRenderer;
    }

    /**
     * {@inheritDoc}
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
            final String nameHtml =
                    renderer.getIndexName();
            final String birthYear = person.getBirthYear();
            final String deathYear = person.getDeathYear();

            return "<a href=\"person?db=" + person.getDbName() + "&amp;id="
                    + person.getString() + "\" class=\"name\">"
                    + nameHtml + dateRangeString(birthYear, deathYear)
                    + " (" + person.getString() + ")</a>";
    }

    /**
     * @param birthYear birth year string
     * @param deathYear death year string
     * @return nicely formatted range string
     */
    private String dateRangeString(final String birthYear,
            final String deathYear) {
        if (birthYear.isEmpty() && deathYear.isEmpty()) {
            return "";
        }
        return " (" + birthYear + "-" + deathYear + ")";
    }
}
