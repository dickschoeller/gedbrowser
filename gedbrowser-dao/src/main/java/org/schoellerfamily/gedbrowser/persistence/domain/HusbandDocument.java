package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Husband;

/**
 * @author Dick Schoeller
 */
public interface HusbandDocument extends GedDocument<Husband> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
