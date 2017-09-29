package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * @author Dick Schoeller
 */
public interface NoteLinkDocument extends GedDocument<NoteLink> {
    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
