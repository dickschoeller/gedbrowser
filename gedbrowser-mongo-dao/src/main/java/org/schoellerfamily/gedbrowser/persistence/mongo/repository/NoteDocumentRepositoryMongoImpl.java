package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;



/**
 * Represents note document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
public class NoteDocumentRepositoryMongoImpl implements
    FindableDocument<Note, NoteDocument>, LastId<NoteDocumentMongo>  {
    /** */
    private final MongoTemplate mongoTemplate;
    /** */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @param string the string
     * @return the resulting note document
     */
    @Override
    public final NoteDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final NoteDocument noteDocument =
                mongoTemplate.findOne(searchQuery, NoteDocumentMongo.class);
        if (noteDocument == null) {
            return null;
        }
        final Note note =
                (Note) toObjConverter.createGedObject(null, noteDocument);
        noteDocument.setGedObject(note);
        return noteDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting note document
     */
    @Override
    public final NoteDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final NoteDocument noteDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (noteDocument == null) {
            return null;
        }
        final Note note = noteDocument.getGedObject();
        note.setParent(rootDocument.getGedObject());
        return noteDocument;
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public final Iterable<NoteDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        final List<NoteDocumentMongo> noteDocumentsMongo =
                mongoTemplate.find(searchQuery, NoteDocumentMongo.class);
        return noteDocumentsMongo.stream()
            .map(noteDocument -> {
                final Note note = (Note) toObjConverter.createGedObject(
                        null, noteDocument);
                noteDocument.setGedObject(note);
                return (NoteDocument) noteDocument;
            }).toList();
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public final Iterable<NoteDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<NoteDocument> noteDocuments =
                findAll(rootDocument.getFilename());
        if (noteDocuments == null) {
            return null;
        }
        for (final NoteDocument noteDocument : noteDocuments) {
            final Note note = noteDocument.getGedObject();
            note.setParent(rootDocument.getGedObject());
        }
        return noteDocuments;
    }

    /**
     * Executes count.
     *
     * @param filename the filename to use
     * @return the resulting long
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        return mongoTemplate.count(searchQuery, NoteDocumentMongo.class);
    }

    /**
     * Returns the long.
     *
     * @param rootDocument the root document
     * @return the resulting long
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, NoteDocumentMongo.class,
                rootDocument.getFilename(), "N");
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public final String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, NoteDocumentMongo.class,
                rootDocument.getFilename(), "N");
    }
}
