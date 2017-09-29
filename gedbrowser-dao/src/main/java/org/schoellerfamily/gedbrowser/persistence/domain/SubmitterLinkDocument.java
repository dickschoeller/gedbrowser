package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * @author Dick Schoeller
 */
public interface SubmitterLinkDocument extends GedDocument<SubmitterLink> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
