package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SourceDocumentRepositoryMongoImpl implements
    FindableDocument<Source, SourceDocument>, LastId<SourceDocumentMongo> {
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
    public final SourceDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final SourceDocument sourceDocument =
                mongoTemplate.findOne(searchQuery, SourceDocumentMongo.class);
        if (sourceDocument == null) {
            return null;
        }
        final Source source =
                (Source) toObjConverter.createGedObject(null, sourceDocument);
        sourceDocument.setGedObject(source);
        return sourceDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final SourceDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final SourceDocument sourceDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (sourceDocument == null) {
            return null;
        }
        final Source source = sourceDocument.getGedObject();
        source.setParent(rootDocument.getGedObject());
        return sourceDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SourceDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<SourceDocumentMongo> sourceDocumentsMongo =
                mongoTemplate.find(searchQuery, SourceDocumentMongo.class);
        if (sourceDocumentsMongo == null) {
            return null;
        }
        final List<SourceDocument> sourceDocuments = new ArrayList<>();
        for (final SourceDocument sourceDocument : sourceDocumentsMongo) {
            final Source source = (Source) toObjConverter.createGedObject(
                    null, sourceDocument);
            sourceDocument.setGedObject(source);
            sourceDocuments.add(sourceDocument);
        }
        return sourceDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<SourceDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<SourceDocument> sourceDocuments =
                findAll(rootDocument.getFilename());
        if (sourceDocuments == null) {
            return null;
        }
        for (final SourceDocument sourceDocument : sourceDocuments) {
            final Source source = sourceDocument.getGedObject();
            source.setParent(rootDocument.getGedObject());
        }
        return sourceDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, SourceDocumentMongo.class);
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
        return lastId(mongoTemplate, SourceDocumentMongo.class,
                rootDocument.getFilename(), "S");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, SourceDocumentMongo.class,
                rootDocument.getFilename(), "S");
    }
}
