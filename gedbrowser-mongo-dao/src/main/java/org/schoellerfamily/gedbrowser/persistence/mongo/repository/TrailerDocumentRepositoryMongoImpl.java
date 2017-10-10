package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class TrailerDocumentRepositoryMongoImpl implements
    FindableDocument<Trailer, TrailerDocument> {
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
    public final TrailerDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final TrailerDocument trailerDocument =
                mongoTemplate.findOne(searchQuery, TrailerDocumentMongo.class);
        if (trailerDocument == null) {
            return null;
        }
        final Trailer trailer = (Trailer) toObjConverter.createGedObject(
                null, trailerDocument);
        trailerDocument.setGedObject(trailer);
        return trailerDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final TrailerDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final TrailerDocument trailerDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (trailerDocument == null) {
            return null;
        }
        final Trailer trailer = trailerDocument.getGedObject();
        trailer.setParent(rootDocument.getGedObject());
        return trailerDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<TrailerDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<TrailerDocumentMongo> trailerDocumentsMongo =
                mongoTemplate.find(searchQuery, TrailerDocumentMongo.class);
        if (trailerDocumentsMongo == null) {
            return null;
        }
        final List<TrailerDocument> trailerDocuments = new ArrayList<>();
        for (final TrailerDocument trailerDocument : trailerDocumentsMongo) {
            final Trailer trailer = (Trailer) toObjConverter.createGedObject(
                    null, trailerDocument);
            trailerDocument.setGedObject(trailer);
            trailerDocuments.add(trailerDocument);
        }
        return trailerDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<TrailerDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<TrailerDocument> trailerDocuments =
                findAll(rootDocument.getFilename());
        if (trailerDocuments == null) {
            return null;
        }
        for (final TrailerDocument trailerDocument : trailerDocuments) {
            final Trailer trailer = trailerDocument.getGedObject();
            trailer.setParent(rootDocument.getGedObject());
        }
        return trailerDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, TrailerDocumentMongo.class);
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
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newId(final RootDocument rootDocument) {
        return "";
    }
}
