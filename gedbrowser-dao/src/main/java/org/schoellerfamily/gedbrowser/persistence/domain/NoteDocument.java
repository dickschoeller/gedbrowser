package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Note;

/**
 * @author Dick Schoeller
 */
public interface NoteDocument extends GedDocument<Note> {
    /**
     * @return the tail string (contents) of the note
     */
    String getTail();

    /**
     * @param tail the tail string (contents) of the note
     */
    void setTail(String tail);

    /**
     * {@inheritDoc}
     */
    @Override
    default void accept(GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
