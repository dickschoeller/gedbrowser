package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Source;

/**
 * Represents the persisted form of source.
 *
 * @author Richard Schoeller
 */
public interface SourceDocument extends GedDocument<Source> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
