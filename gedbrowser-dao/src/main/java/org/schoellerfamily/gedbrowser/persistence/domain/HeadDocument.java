package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Head;

/**
 * @author Dick Schoeller
 */
public interface HeadDocument extends GedDocument<Head> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
