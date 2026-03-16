package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;

/**
 * Represents the persisted form of submission link.
 *
 * @author Richard Schoeller
 */
public interface SubmissionLinkDocument extends GedDocument<SubmissionLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
