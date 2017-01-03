package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class RootDocumentRepositoryMongoImpl implements
    FindableDocument<Root, RootDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    /** */
    @Autowired
    private transient FinderStrategy finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public final RootDocument findByFileAndString(
            final String filename, final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final RootDocument rootDocument = mongoTemplate.findOne(searchQuery,
                RootDocumentMongo.class);
        if (rootDocument == null) {
            return null;
        }
        final Root root = (Root) GedDocumentMongoFactory.getInstance().
                createRoot(rootDocument, finder);
        rootDocument.setGedObject(root);
        return rootDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final RootDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        throw new IllegalArgumentException(
                "Not implementable for this document type");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<RootDocument> findAll(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        final List<RootDocumentMongo> rootDocumentsMongo =
                mongoTemplate.find(searchQuery, RootDocumentMongo.class);
        if (rootDocumentsMongo == null) {
            return null;
        }
        final List<RootDocument> rootDocuments = new ArrayList<>();
        for (final RootDocument rootDocument : rootDocumentsMongo) {
            final Root root =
                    (Root) GedDocumentMongoFactory.getInstance()
                        .createGedObject(null, rootDocument);
            rootDocument.setGedObject(root);
            rootDocuments.add(rootDocument);
        }
        return rootDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Iterable<RootDocument> findAll(
            final RootDocument rootDocument) {
        final Iterable<RootDocument> rootDocuments =
                findAll(rootDocument.getFilename());
        if (rootDocuments == null) {
            return null;
        }
        for (final RootDocument rootDocumentOut : rootDocuments) {
            final Root root = rootDocumentOut.getGedObject();
            root.setParent(rootDocumentOut.getGedObject());
        }
        return rootDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where("filename").is(filename));
        return mongoTemplate.count(searchQuery, RootDocumentMongo.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }
}
