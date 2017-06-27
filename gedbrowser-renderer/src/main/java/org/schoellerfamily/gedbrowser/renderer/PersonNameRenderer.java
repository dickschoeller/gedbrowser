package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * @author Dick Schoeller
 */
public interface PersonNameRenderer {
    /**
     * @return whether the current person is confidential.
     */
    boolean isConfidential();

    /**
     * @return whether the current person is confidential.
     */
    boolean isHiddenLiving();

    /**
     * @param attribute The sub-object to render.
     * @return The renderer.
     */
    GedRenderer<? extends GedObject> createGedRenderer(GedObject attribute);

    /**
     * @return the GedObject
     */
    Person getGedObject();

    /**
     * @return the name string in title format.
     */
    default String getTitleName() {
        final Name name = getGedObject().getName();
        if (isConfidential()) {
            return "Confidential";
        } else if (isHiddenLiving()) {
            return "Living";
        } else {
            final GedRenderer<?> nameRenderer = createGedRenderer(name);
            return nameRenderer.getNameHtml();
        }
    }

    /**
     * @return Get the whole name without markup.
     */
    default String getWholeName() {
        if (isConfidential()) {
            return "Confidential";
        } else if (isHiddenLiving()) {
            return "Living";
        }
        final Name name = getGedObject().getName();

        final String prefix =
                RenderingContextRenderer.escapeString(name.getPrefix());
        final String surname =
                RenderingContextRenderer.escapeString(name.getSurname());
        final String suffix =
                RenderingContextRenderer.escapeString(" ", name.getSuffix());

        return String.format("%s %s%s", prefix, surname, suffix);
    }

    /**
     * @return surname if not confidential
     */
    default String getSurname() {
        if (isConfidential() || isHiddenLiving()) {
            return "?";
        } else {
            return getGedObject().getSurname();
        }
    }

    /**
     * @return surname first letter if not confidential
     */
    default String getSurnameLetter() {
        if (isConfidential() || isHiddenLiving()) {
            return "?";
        } else {
            return getGedObject().getSurnameLetter();
        }
    }
}
