package org.schoellerfamily.gedbrowser.datamodel.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

/**
 * Visitor for determining a person's relationships.
 *
 * @author Dick Schoeller
 */
public final class PersonConfidentialVisitor implements GedObjectVisitor {
    /**
     * Has a confidential setting.
     */
    private boolean isConfidential;

    /**
     * @return true if this person is confidential
     */
    public boolean isConfidential() {
        return isConfidential;
    }

    /**
     * Visit an Attribute. Certain Attributes contribute interest data.
     *
     * @see GedObjectVisitor#visit(Attribute)
     */
    @Override
    public void visit(final Attribute attribute) {
        if ("Restriction".equals(attribute.getString())
                && "confidential".equals(attribute.getTail())) {
            isConfidential = true;
        }
    }

    /**
     * Visit a Person. Look through the attributes of a Person for
     * relationships (FamC and FamS) and interesting Attributes.
     *
     * @see GedObjectVisitor#visit(Person)
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
