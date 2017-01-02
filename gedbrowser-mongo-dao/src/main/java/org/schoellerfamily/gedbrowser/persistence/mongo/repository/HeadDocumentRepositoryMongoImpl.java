package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class HeadDocumentRepositoryMongoImpl implements
    FindableDocument<Head, HeadDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public final HeadDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final HeadDocument headDocument =
                mongoTemplate.findOne(searchQuery, HeadDocumentMongo.class);
        if (headDocument == null) {
            return null;
        }
        final Head head = (Head) GedDocumentMongoFactory.getInstance().
                createGedObject(null, headDocument);
        headDocument.setGedObject(head);
        return headDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HeadDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final HeadDocument headDocument =
                findByFileAndString(rootDocument.getFilename(), string);
        if (headDocument == null) {
            return null;
        }
        final Head head = headDocument.getGedObject();
        head.setParent(rootDocument.getGedObject());
        return headDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<HeadDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<HeadDocumentMongo> headDocumentsMongo =
                mongoTemplate.find(searchQuery, HeadDocumentMongo.class);
        if (headDocumentsMongo == null) {
            return null;
        }
        final List<HeadDocument> headDocuments = new ArrayList<>();
        for (final HeadDocument headDocument : headDocumentsMongo) {
            final Head head =
                    (Head) GedDocumentMongoFactory.getInstance()
                        .createGedObject(null, headDocument);
            headDocument.setGedObject(head);
            headDocuments.add(headDocument);
        }
        return headDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<HeadDocument> findAll(final RootDocument rootDocument) {
        final Iterable<HeadDocument> headDocuments =
                findAll(rootDocument.getFilename());
        if (headDocuments == null) {
            return null;
        }
        for (final HeadDocument headDocument : headDocuments) {
            final Head head = headDocument.getGedObject();
            head.setParent(rootDocument.getGedObject());
        }
        return headDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, HeadDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
