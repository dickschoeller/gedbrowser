package org.schoellerfamily.geoservice.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.repository.GeocodableDocument;

import com.google.maps.model.GeocodingResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

/**
 * Defines persistence operations for geo document repository mongo.
 *
 * @author Richard Schoeller
 */
@RequiredArgsConstructor
public class GeoDocumentRepositoryMongo implements GeocodableDocument {
    /** Mongo collection for CRUD operations. */
    private final MongoCollection<Document> collection;

    /** JSON mapper for result payload conversion. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Primary constructor for repository wiring.
     *
     * @param mongoDatabase MongoDB database
     */
    public GeoDocumentRepositoryMongo(final MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("geocode");
    }

    /**
     * Remove all documents.
     */
    public void deleteAll() {
        collection.deleteMany(new Document());
    }

    /**
     * Find all documents.
     *
     * @return all documents
     */
    public Iterable<GeoDocumentMongo> findAll() {
        final FindIterable<Document> documents = collection.find();
        final List<GeoDocumentMongo> result = new ArrayList<>();
        for (final Document document : documents) {
            result.add(fromDocument(document));
        }
        return result;
    }

    /**
     * Count all documents.
     *
     * @return number of documents
     */
    public long count() {
        return collection.countDocuments();
    }

    /**
     * Save a document.
     *
     * @param document document to save
     */
    public void save(final GeoDocumentMongo document) {
        if (document == null || document.getName() == null) {
            return;
        }
        collection.replaceOne(Filters.eq("_id", document.getName()),
                toDocument(document), new ReplaceOptions().upsert(true));
    }

    /**
     * Delete a document.
     *
     * @param document document to delete
     */
    public void delete(final GeoDocumentMongo document) {
        if (document == null || document.getName() == null) {
            return;
        }
        collection.deleteOne(Filters.eq("_id", document.getName()));
    }

    /**
     * Find a document by place name.
     *
     * @param placeName place name key
     * @return matching document or null
     */
    @Override
    public GeoDocumentMongo find(final String placeName) {
        final Document persisted = collection.find(Filters.eq("_id", placeName)).first();
        final GeoDocumentMongo document = fromDocument(persisted);
        if (document == null) {
            return null;
        }
        document.setGeoItem(
                GeoDocumentMongoFactory.getInstance().createGeoCodeItem(document));
        return document;
    }

    private Document toDocument(final GeoDocumentMongo document) {
        final Document persisted = new Document("_id", document.getName())
                .append("name", document.getName())
                .append("modernName", document.getModernName());
        final GeocodingResult geocodingResult = document.getResult();
        if (geocodingResult != null) {
            persisted.append("resultJson", serializeResult(geocodingResult));
        }
        return persisted;
    }

    private GeoDocumentMongo fromDocument(final Document persisted) {
        if (persisted == null) {
            return null;
        }
        final Object id = persisted.get("_id");
        final String name = persisted.getString("name") != null
                ? persisted.getString("name")
                : (id == null ? null : String.valueOf(id));
        final String modernName = persisted.getString("modernName");
        String resultJson = persisted.getString("resultJson");
        if (resultJson == null && persisted.get("result") != null) {
            try {
                resultJson = OBJECT_MAPPER.writeValueAsString(persisted.get("result"));
            } catch (Exception ex) {
                throw new IllegalStateException(
                        "Unable to serialize legacy geocoding result", ex);
            }
        }
        final GeocodingResult geocodingResult = deserializeResult(resultJson);
        final GeoDocumentMongo document = new GeoDocumentMongo();
        document.loadPersistedValues(name, modernName, geocodingResult);
        return document;
    }

    private String serializeResult(final GeocodingResult geocodingResult) {
        try {
            return OBJECT_MAPPER.writeValueAsString(geocodingResult);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to serialize geocoding result", ex);
        }
    }

    private GeocodingResult deserializeResult(final String resultJson) {
        if (resultJson == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(resultJson, GeocodingResult.class);
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Unable to deserialize geocoding result", ex);
        }
    }
}
