package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Wife;

/**
 * Represents the persisted form of wife.
 *
 * @author Richard Schoeller
 */
public interface WifeDocument extends GedDocument<Wife> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
