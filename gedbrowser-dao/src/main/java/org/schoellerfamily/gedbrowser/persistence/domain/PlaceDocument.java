package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * @author Dick Schoeller
 */
public interface PlaceDocument extends GedDocument<Place> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
