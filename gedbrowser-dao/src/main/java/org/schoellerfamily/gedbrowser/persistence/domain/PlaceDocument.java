package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * Represents the persisted form of place.
 *
 * @author Richard Schoeller
 */
public interface PlaceDocument extends GedDocument<Place> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
