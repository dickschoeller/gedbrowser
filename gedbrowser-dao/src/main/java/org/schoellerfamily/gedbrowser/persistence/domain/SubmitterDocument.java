package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;

/**
 * @author Dick Schoeller
 */
public interface SubmitterDocument extends GedDocument<Submitter> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
