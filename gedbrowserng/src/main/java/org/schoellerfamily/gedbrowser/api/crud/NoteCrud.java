package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.NoteDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
public class NoteCrud
    extends OperationsEnabler<Note>
    implements CrudOperations<Note, NoteDocument, ApiNote>,
        ObjectCrud<ApiNote> {

    /**
     * Creates a new NoteCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public NoteCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the repository.
     *
     * @return the repository
     */
    @Override
    public FindableDocument<Note, NoteDocument> getRepository() {
        return ((NoteDocumentRepositoryMongo) getRepositoryManager().get(Note.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Note> getGedClass() {
        return Note.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param note the note
     * @return the resulting api note
     */
    @Override
    public ApiNote createOne(final String db,
            final ApiNote note) {
        log.info("Entering create note in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), note,
            (i, id) -> i.toBuilder().string(id).build());
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiNote> readAll(
            final String db) {
        log.info("Entering notes, db: {}", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api note
     */
    @Override
    public ApiNote readOne(
            final String db,
            final String id) {
        log.info("Entering note, db: {}, id: {}", db, id);
        return getD2dm().convert(read(getRepositoryManager(), db, id));
    }


    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param note the note
     * @return the resulting api note
     */
    @Override
    public ApiNote updateOne(final String db,
            final String id,
            final ApiNote note) {
        log.info("Entering update note in db: {}", db);
        if (!id.equals(note.getString())) {
            return null;
        }
        return update(readRoot(getRepositoryManager(), db), note);
    }

    /**
     * Returns the api note.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api note
     */
    @Override
    public ApiNote deleteOne(
            final String db,
            final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
