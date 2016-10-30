package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
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
        final Trailer trailer = (Trailer) GedDocumentMongoFactory.getInstance().
                createGedObject(null, trailerDocument);
        trailerDocument.setGedObject(trailer);
        return trailerDocument;
    }

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
}
