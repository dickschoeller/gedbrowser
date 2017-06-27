package org.schoellerfamily.gedbrowser.renderer;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GetDateVisitor;

/**
 * @author Dick Schoeller
 */
public interface PersonLifeSpanRenderer {
    /**
     * @return whether the current person is confidential.
     */
    boolean isConfidential();

    /**
     * @return whether the current person is confidential.
     */
    boolean isHiddenLiving();

    /**
     * @return the GedObject
     */
    Person getGedObject();

    /**
     * @return lifespan string.
     */
    default String getLifeSpanString() {
        if (isConfidential() || isHiddenLiving()) {
            return "";
        }

        return String.format("(%s-%s)", date("Birth"), date("Death"));
    }

    /**
     * @param type the event type to find a date for
     * @return the date string
     */
    default String date(final String type) {
        final GetDateVisitor birthVisitor = new GetDateVisitor(type);
        getGedObject().accept(birthVisitor);
        return birthVisitor.getDate();
    }
}
