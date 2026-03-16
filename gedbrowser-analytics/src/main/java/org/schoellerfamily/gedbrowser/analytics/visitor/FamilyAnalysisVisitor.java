package org.schoellerfamily.gedbrowser.analytics.visitor;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;

/**
 * Visitor aids in the process of analyzing the order of events in a family.
 *
 * @author Dick Schoeller
 */
public final class FamilyAnalysisVisitor extends AbstractAnalysisVisitor {
    /**
     * Executes visit.
     *
     * @param family the family
     */
    @Override
    public void visit(final Family family) {
        for (final GedObject gob : family.getAttributes()) {
            gob.accept(this);
        }
    }
}
