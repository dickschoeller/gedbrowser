package org.schoellerfamily.gedbrowser.analytics.visitor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class PersonAnalysisVisitor extends AbstractAnalysisVisitor {
    /**
     * Visit a Person. This is the primary focus of the visitation. From
     * here, interesting information is gathered from the attributes.
     *
     * @see GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }
}
