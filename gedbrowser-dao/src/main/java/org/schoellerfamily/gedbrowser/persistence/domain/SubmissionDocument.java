package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Submission;

/**
 * @author Dick Schoeller
 */
public interface SubmissionDocument extends GedDocument<Submission> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
