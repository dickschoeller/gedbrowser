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
     * Executes visit.
     *
     * @param person the person
     */
    @Override
    public void visit(final Person person) {
        for (final GedObject gob : person.getAttributes()) {
            gob.accept(this);
        }
    }
}
