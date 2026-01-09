package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.NoteLink;

/**
 * @author Dick Schoeller
 */
public interface NoteLinkDocument extends GedDocument<NoteLink> {
    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
