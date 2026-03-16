package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Child;

/**
 * Represents the persisted form of child.
 *
 * @author Richard Schoeller
 */
public interface ChildDocument extends GedDocument<Child> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
