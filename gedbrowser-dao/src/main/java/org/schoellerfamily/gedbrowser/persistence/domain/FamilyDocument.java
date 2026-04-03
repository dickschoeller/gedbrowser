package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Family;

/**
 * Represents the persisted form of family.
 *
 * @author Richard Schoeller
 */
public interface FamilyDocument extends GedDocument<Family> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
