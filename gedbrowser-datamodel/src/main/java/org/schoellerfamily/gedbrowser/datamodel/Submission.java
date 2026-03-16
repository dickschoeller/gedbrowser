package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public final class Submission extends GedObject {
    /**
     * Creates a new Submission.
     *
     */
    public Submission() {
        super();
    }

    /**
     * Creates a new Submission.
     *
     * @param parent the parent
     * @param objectId the unique identifier for object
     */
    public Submission(final GedObject parent, final ObjectId objectId) {
        super(parent, objectId.getIdString());
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
