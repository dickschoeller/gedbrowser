package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public class PersonNameHtmlRenderer implements NameHtmlRenderer {
    /**
     * Holder for the PersonRenderer that is using this helper.
     */
    private final transient PersonRenderer personRenderer;

    /**
     * Constructor.
     *
     * @param personRenderer the PersonRenderer that is using this helper.
     */
    protected PersonNameHtmlRenderer(final PersonRenderer personRenderer) {
        this.personRenderer = personRenderer;
    }

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
        final String nameHtml =
                renderer.getNameHtml();

        String spacer;
        if (personRenderer.getLifeSpanString().isEmpty()) {
            spacer = "";
        } else {
            spacer = " ";
        }

        return "<a href=\"person?db=" + person.getDbName() + "&amp;id="
                + person.getString() + "\" class=\"name\">" + nameHtml + " ("
                + person.getString() + ")" + spacer
                + personRenderer.getLifeSpanString() + "</a>";
    }
}
