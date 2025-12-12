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
    extends OperationsEnabler<Note, NoteDocument>
    implements CrudOperations<Note, NoteDocument, ApiNote>,
        ObjectCrud<ApiNote> {

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public NoteCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Note, NoteDocument> getRepository() {
        return ((NoteDocumentRepositoryMongo) getRepositoryManager().get(Note.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Note> getGedClass() {
        return Note.class;
    }

    /**
     * @param db the name of the db to access
     * @param note the data for the note
     * @return the note as created
     */
    @Override
    public ApiNote createOne(final String db,
            final ApiNote note) {
        log.info("Entering create note in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), note, (i, id) ->
            new ApiNote(i.getType(), id, i.getAttributes(), i.getTail()));
    }

    /**
     * @param db the name of the db to access
     * @return the list of notes
     */
    @Override
    public List<ApiNote> readAll(
            final String db) {
        log.info("Entering notes, db: {}", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the note
     */
    @Override
    public ApiNote readOne(
            final String db,
            final String id) {
        log.info("Entering note, db: {}, id: {}", db, id);
        return getD2dm().convert(read(getRepositoryManager(), db, id));
    }


    /**
     * @param db the name of the db to access
     * @param id the id of the note to update
     * @param note the data for the note
     * @return the note as created
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
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the deleted object
     */
    @Override
    public ApiNote deleteOne(
            final String db,
            final String id) {
        return delete(readRoot(getRepositoryManager(), db), id);
    }
}
