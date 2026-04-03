package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * Represents the persisted form of submitter.
 *
 * @author Richard Schoeller
 */
public interface SubmitterDocument extends GedDocument<Submitter> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
