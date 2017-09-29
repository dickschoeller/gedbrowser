package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;

/**
 * @author Dick Schoeller
 */
public interface SubmissionLinkDocument extends GedDocument<SubmissionLink> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
