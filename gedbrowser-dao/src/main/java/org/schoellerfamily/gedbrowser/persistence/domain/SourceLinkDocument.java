package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SourceLink;

/**
 * @author Dick Schoeller
 */
public interface SourceLinkDocument extends GedDocument<SourceLink> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
