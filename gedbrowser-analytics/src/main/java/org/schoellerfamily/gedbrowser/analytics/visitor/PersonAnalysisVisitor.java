package org.schoellerfamily.gedbrowser.analytics.visitor;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;

import lombok.NoArgsConstructor;

/**
 * Visitor that analyzes a Person object.
 *
 * @author Dick Schoeller
 */
@NoArgsConstructor
public final class PersonAnalysisVisitor extends AbstractAnalysisVisitor {
    /**
     * Visit a Person. This is the primary focus of the visitation. From
     * here, interesting information is gathered from the attributes.
     *
     * @see org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor#visit(Person)
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }
}
