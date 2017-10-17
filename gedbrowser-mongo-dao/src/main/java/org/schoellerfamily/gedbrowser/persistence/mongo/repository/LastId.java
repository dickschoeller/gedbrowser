package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @param <T> the GedDocumentMongo type we are dealing with
 * @author Dick Schoeller
 */
public interface LastId<T extends GedDocumentMongo<?>> {
    /**
     * @param mongoTemplate the MongoDB access template
     * @param clazz the class of the type we want to return
     * @param filename the filename associated with the data set
     * @param baseId the base ID string for this type
     * @return the last matching ID
     */
    default String lastId(final MongoTemplate mongoTemplate,
            final Class<T> clazz, final String filename,
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
     * @param mongoTemplate the MongoDB access template
     * @param clazz the class of the type we want to return
     * @param filename the filename associated with the data set
     * @param baseId the base ID string for this type
     * @return the last matching ID
     */
    default String newId(final MongoTemplate mongoTemplate,
            final Class<T> clazz, final String filename,
            final String baseId) {
        String last = lastId(mongoTemplate, clazz, filename, baseId);
        String number = last.replace(baseId, "");
        if (number.isEmpty()) {
            return baseId + "1";
        }
        int newNumber = Integer.parseInt(number) + 1;
        return baseId + newNumber;
    }
}
