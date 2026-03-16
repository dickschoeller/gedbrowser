package org.schoellerfamily.gedbrowser.datamodel.visitor;

import lombok.NoArgsConstructor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Visits person confidential elements and applies visitor logic.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public final class PersonConfidentialVisitor implements GedObjectVisitor {

    /**
     * Has a confidential setting.
     */
    private boolean isConfidential;

    /**
     * Checks whether confidential.
     *
     * @return true if the condition is met; otherwise false
     */
    public boolean isConfidential() {
        return isConfidential;
    }

    /**
     * Executes visit.
     *
     * @param attribute the attribute
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Restriction".equals(attribute.getString())
                && "confidential".equals(attribute.getTail())) {
            isConfidential = true;
        }
    }

    /**
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
            if (isConfidential) {
                break;
            }
        }
    }
}
