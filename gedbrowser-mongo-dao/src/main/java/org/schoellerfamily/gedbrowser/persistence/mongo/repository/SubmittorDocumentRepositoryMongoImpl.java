package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SubmittorDocumentRepositoryMongoImpl implements
    FindableDocument<Submittor, SubmittorDocument> {
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
    public final SubmittorDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SubmittorDocument submittorDocument =
                mongoTemplate.findOne(searchQuery,
                        SubmittorDocumentMongo.class);
        if (submittorDocument == null) {
            return null;
        }
        final Submittor submittor = (Submittor) toObjConverter.createGedObject(
                null, submittorDocument);
        submittorDocument.setGedObject(submittor);
        return submittorDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SubmittorDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SubmittorDocument submittorDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (submittorDocument == null) {
            return null;
        }
        final Submittor submittor = submittorDocument.getGedObject();
        submittor.setParent(rootDocument.getGedObject());
        return submittorDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmittorDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SubmittorDocumentMongo> submittorDocumentsMongo =
                mongoTemplate.find(searchQuery, SubmittorDocumentMongo.class);
        if (submittorDocumentsMongo == null) {
            return null;
        }
        final List<SubmittorDocument> submittorDocuments = new ArrayList<>();
        for (final SubmittorDocument submittorDocument
                : submittorDocumentsMongo) {
            final Submittor submittor = (Submittor) toObjConverter
                    .createGedObject(null, submittorDocument);
            submittorDocument.setGedObject(submittor);
            submittorDocuments.add(submittorDocument);
        }
        return submittorDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SubmittorDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SubmittorDocument> submittorDocuments =
                findAll(rootDocument.getFilename());
        if (submittorDocuments == null) {
            return null;
        }
        for (final SubmittorDocument submittorDocument : submittorDocuments) {
            final Submittor submittor = submittorDocument.getGedObject();
            submittor.setParent(rootDocument.getGedObject());
        }
        return submittorDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SubmittorDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
