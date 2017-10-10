package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class NoteDocumentRepositoryMongoImpl implements
    FindableDocument<Note, NoteDocument>, LastId<NoteDocumentMongo>  {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public final NoteDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
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
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public final Iterable<NoteDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<NoteDocumentMongo> noteDocumentsMongo =
                mongoTemplate.find(searchQuery, NoteDocumentMongo.class);
        if (noteDocumentsMongo == null) {
            return null;
        }
        final List<NoteDocument> noteDocuments = new ArrayList<>();
        for (final NoteDocument noteDocument : noteDocumentsMongo) {
            final Note note = (Note) toObjConverter.createGedObject(
                    null, noteDocument);
            noteDocument.setGedObject(note);
            noteDocuments.add(noteDocument);
        }
        return noteDocuments;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, NoteDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, NoteDocumentMongo.class,
                rootDocument.getFilename(), "N");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, NoteDocumentMongo.class,
                rootDocument.getFilename(), "N");
    }
}
