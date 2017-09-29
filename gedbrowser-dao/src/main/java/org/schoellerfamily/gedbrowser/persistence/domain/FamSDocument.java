package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamS;

/**
 * @author Dick Schoeller
 */
public interface FamSDocument extends GedDocument<FamS> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
