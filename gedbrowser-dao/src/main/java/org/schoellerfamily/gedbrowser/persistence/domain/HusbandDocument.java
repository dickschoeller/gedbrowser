package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Husband;

/**
 * Represents the persisted form of husband.
 *
 * @author Richard Schoeller
 */
public interface HusbandDocument extends GedDocument<Husband> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
