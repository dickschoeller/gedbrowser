package org.schoellerfamily.geoservice.persistence.mongo.repository;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.repository.GeocodableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public class GeoDocumentRepositoryMongoImpl
        implements GeocodableDocument {
    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public final GeoDocument find(final String placeName) {
        final Query searchQuery =
                new Query(Criteria.where("name").is(placeName));
        final GeoDocumentMongo geoDocument =
                mongoTemplate.findOne(searchQuery, GeoDocumentMongo.class);
        if (geoDocument == null) {
            return null;
        }
        final GeoCodeItem item = GeoDocumentMongoFactory.getInstance().
                createGeoCodeItem(geoDocument);
        geoDocument.setGeoItem(item);
        return geoDocument;
    }
}
