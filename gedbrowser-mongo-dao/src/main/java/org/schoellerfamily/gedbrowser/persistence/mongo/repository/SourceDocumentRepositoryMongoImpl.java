package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class SourceDocumentRepositoryMongoImpl implements
    FindableDocument<Source, SourceDocument> {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

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
        final Source source = (Source) GedDocumentMongoFactory.getInstance().
                createGedObject(null, sourceDocument);
        sourceDocument.setGedObject(source);
        return sourceDocument;
    }

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
}
