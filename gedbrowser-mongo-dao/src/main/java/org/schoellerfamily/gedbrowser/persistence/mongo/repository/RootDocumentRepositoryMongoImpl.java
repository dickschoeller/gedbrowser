package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

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

    @Override
    public final RootDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        throw new IllegalArgumentException(
                "Not implementable for this document type");
    }
}
