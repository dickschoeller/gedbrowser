package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Defines the contract for visitable object.
 *
 * @author Richard Schoeller
 */
public interface VisitableObject {
    /**
     * Hook for using the visitor design pattern to accumulate information
     * about a GedObject and its children.
     *
     * @param visitor the visitor
     */
    void accept(GedObjectVisitor visitor);
}
