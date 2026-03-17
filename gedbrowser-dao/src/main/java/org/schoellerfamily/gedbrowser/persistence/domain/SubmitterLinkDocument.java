package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * Represents the persisted form of submitter link.
 *
 * @author Richard Schoeller
 */
public interface SubmitterLinkDocument extends GedDocument<SubmitterLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
