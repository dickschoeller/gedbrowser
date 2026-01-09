package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;

/**
 * @author Dick Schoeller
 */
public interface TrailerDocument extends GedDocument<Trailer> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
