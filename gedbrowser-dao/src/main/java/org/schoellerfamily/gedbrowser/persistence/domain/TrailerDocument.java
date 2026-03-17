package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * Represents the persisted form of trailer.
 *
 * @author Richard Schoeller
 */
public interface TrailerDocument extends GedDocument<Trailer> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
