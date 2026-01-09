package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;

/**
 * @author Dick Schoeller
 */
public interface SubmitterLinkDocument extends GedDocument<SubmitterLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
