package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SubmitterDocumentRepositoryMongoImpl implements
    FindableDocument<Submitter, SubmitterDocument> {
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
    public final SubmitterDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmitterDocument submitterDocument =
                mongoTemplate.findOne(searchQuery,
                        SubmitterDocumentMongo.class);
        if (submitterDocument == null) {
            return null;
        }
        final Submitter submitter = (Submitter) toObjConverter.createGedObject(
                null, submitterDocument);
        submitterDocument.setGedObject(submitter);
        return submitterDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SubmitterDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SubmitterDocument submitterDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (submitterDocument == null) {
            return null;
        }
        final Submitter submitter = submitterDocument.getGedObject();
        submitter.setParent(rootDocument.getGedObject());
        return submitterDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmitterDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SubmitterDocumentMongo> submitterDocumentsMongo =
                mongoTemplate.find(searchQuery, SubmitterDocumentMongo.class);
        if (submitterDocumentsMongo == null) {
            return null;
        }
        final List<SubmitterDocument> submitterDocuments = new ArrayList<>();
        for (final SubmitterDocument submitterDocument
                : submitterDocumentsMongo) {
            final Submitter submitter = (Submitter) toObjConverter
                    .createGedObject(null, submitterDocument);
            submitterDocument.setGedObject(submitter);
            submitterDocuments.add(submitterDocument);
        }
        return submitterDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmitterDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SubmitterDocument> submitterDocuments =
                findAll(rootDocument.getFilename());
        if (submitterDocuments == null) {
            return null;
        }
        for (final SubmitterDocument submitterDocument : submitterDocuments) {
            final Submitter submitter = submitterDocument.getGedObject();
            submitter.setParent(rootDocument.getGedObject());
        }
        return submitterDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SubmitterDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
