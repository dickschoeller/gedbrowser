package org.schoellerfamily.geoservice.persistence.mongo.repository;

import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.repository.GeocodableDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * This does the work of reading getting geocode items from MongoDB.
 *
 * @author Dick Schoeller
 */
@Component
@RequiredArgsConstructor
public class GeoDocumentRepositoryMongoImpl
        implements GeocodableDocument {
    /** */
    private final MongoTemplate mongoTemplate;

    /**
     * Finds a value.
     *
     * @param placeName the place name to use
     * @return the resulting geo document
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
