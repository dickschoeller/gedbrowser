package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * Represents the persisted form of source link.
 *
 * @author Richard Schoeller
 */
public interface SourceLinkDocument extends GedDocument<SourceLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
