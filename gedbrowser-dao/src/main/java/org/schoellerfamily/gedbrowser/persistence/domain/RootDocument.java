package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * Represents the persisted form of root.
 *
 * @author Richard Schoeller
 */
public interface RootDocument extends GedDocument<Root> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
