package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class NoteCrud
    extends OperationsEnabler<Note, NoteDocument>
    implements CrudOperations<Note, NoteDocument, ApiNote> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public NoteCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Note, NoteDocument> getRepository() {
        return getRepositoryManager().getNoteDocumentRepository();
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
    public ApiObject createNote(final String db,
            final ApiNote note) {
        logger.info("Entering create note in db: " + db);
        return create(readRoot(db), note, (i, id) ->
            new ApiNote(i.getType(), id, i.getAttributes(), i.getTail()));
    }

    /**
     * @param db the name of the db to access
     * @return the list of notes
     */
    public List<ApiNote> readNotes(
            final String db) {
        logger.info("Entering notes, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the note
     */
    public ApiNote readNote(
            final String db,
            final String id) {
        logger.info("Entering note, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }


    /**
     * @param db the name of the db to access
     * @param id the id of the note to update
     * @param note the data for the note
     * @return the note as created
     */
    public ApiObject updateNote(final String db,
            final String id,
            final ApiNote note) {
        logger.info("Entering update note in db: " + db);
        if (!id.equals(note.getString())) {
            return null;
        }
        return update(readRoot(db), note);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the deleted object
     */
    public ApiNote deleteNote(
            final String db,
            final String id) {
        return delete(readRoot(db), id);
    }
}
