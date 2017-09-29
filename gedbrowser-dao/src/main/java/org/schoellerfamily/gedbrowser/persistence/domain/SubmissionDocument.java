package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Submission;

/**
 * @author Dick Schoeller
 */
public interface SubmissionDocument extends GedDocument<Submission> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
