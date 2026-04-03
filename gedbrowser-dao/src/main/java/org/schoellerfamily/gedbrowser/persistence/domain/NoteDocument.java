package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Note;

/**
 * Represents the persisted form of note.
 *
 * @author Richard Schoeller
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

    @Override
    default void accept(final GedDocumentVisitor visitor) {
        visitor.visit(this);
    }
}
