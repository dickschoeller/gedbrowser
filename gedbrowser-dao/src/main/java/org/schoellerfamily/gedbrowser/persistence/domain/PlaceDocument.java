package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Place;

/**
 * @author Dick Schoeller
 */
public interface PlaceDocument extends GedDocument<Place> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
