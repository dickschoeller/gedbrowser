package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Name;

/**
 * @author Dick Schoeller
 */
public interface NameDocument extends GedDocument<Name> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
