package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides note repo values to calling code.
 *
 * @author Richard Schoeller
 */
public interface NoteRepoProvider extends HasRepoMap {
    /**
     * @param repository the repository
     */
    @Autowired
    default void setNoteDocumentRepository(final NoteDocumentRepositoryMongo repository) {
        getMap().put(Note.class, repository);
    }

    /**
     * @return the repository
     */
    default NoteDocumentRepositoryMongo getNoteDocumentRepository() {
        return (NoteDocumentRepositoryMongo) getMap().get(Note.class);
    }
}
