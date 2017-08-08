package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * @author Dick Schoeller
 */
public class Submission extends GedObject {
    /**
     * Constructor.
     */
    public Submission() {
        super();
    }

    /**
     * Constructor.
     *
     * @param parent the parent object, generally the root
     * @param objectId the ID of this submission
     */
    public Submission(final GedObject parent, final ObjectId objectId) {
        super(parent, objectId.getIdString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
