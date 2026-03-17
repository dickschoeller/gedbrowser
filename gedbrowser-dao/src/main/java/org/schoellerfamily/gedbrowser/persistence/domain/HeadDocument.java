package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * Represents the persisted form of head.
 *
 * @author Richard Schoeller
 */
public interface HeadDocument extends GedDocument<Head> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
