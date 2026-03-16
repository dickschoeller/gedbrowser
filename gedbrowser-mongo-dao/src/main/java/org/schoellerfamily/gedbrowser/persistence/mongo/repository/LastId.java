package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Defines persistence operations for last id.
 *
 * @param <T> the GedDocumentMongo type we are dealing with
 * @author Richard Schoeller
 */
public interface LastId<T extends GedDocumentMongo<?>> {
    /**
     * Performs last id.
     *
     * @param mongoTemplate the MongoDB access template
     * @param clazz the document class to query
     * @param filename the dataset filename to restrict the search
     * @param baseId the base ID prefix to match against
     * @return the last matching ID
     */
    default String lastId(final MongoTemplate mongoTemplate,
            final Class<T> clazz,
            final String filename,
            final String baseId) {
        final Query query = new Query(Criteria
                .where("filename").is(filename)
                .and("string").regex(baseId + "[0-9]*"));
        query.fields().include("string");

        final List<T> documents = mongoTemplate.find(query, clazz);
        documents.sort(new GetStringComparator());
        final int size = documents.size();
        if (size == 0) {
            return baseId;
        }
        return documents.get(size - 1).getString();
    }

    /**
     * Performs new id.
     *
     * @param mongoTemplate the MongoDB access template
     * @param clazz the document class to query
     * @param filename the dataset filename to restrict the search
     * @param baseId the base ID prefix to use when generating the new ID
     * @return the next available ID
     */
    default String newId(final MongoTemplate mongoTemplate,
            final Class<T> clazz,
            final String filename,
            final String baseId) {
        final String last = lastId(mongoTemplate, clazz, filename, baseId);
        final String number = last.replace(baseId, "");
        if (number.isEmpty()) {
            return baseId + "1";
        }
        final int newNumber = Integer.parseInt(number) + 1;
        return baseId + newNumber;
    }
}
