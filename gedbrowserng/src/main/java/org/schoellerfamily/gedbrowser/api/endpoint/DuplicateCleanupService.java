package org.schoellerfamily.gedbrowser.api.endpoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.DeleteResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Removes duplicate documents by (filename, string) across key collections.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DuplicateCleanupService {
    /** */
    private final MongoTemplate mongoTemplate;

    /**
     * @return number of deleted documents per collection and total count
     */
    public Map<String, Integer> cleanup() {
        final Map<String, Integer> deleted = new LinkedHashMap<>();
        deleted.put("roots", Math.toIntExact(cleanupCollection("roots")));
        deleted.put("persons", Math.toIntExact(cleanupCollection("persons")));
        deleted.put("sources", Math.toIntExact(cleanupCollection("sources")));
        deleted.put("submitters", Math.toIntExact(cleanupCollection("submitters")));
        final int total = deleted.get("roots")
            + deleted.get("persons")
            + deleted.get("sources")
            + deleted.get("submitters");
        deleted.put("total", total);
        return deleted;
    }

    /**
     * Log discovered duplicate IDs without deleting any documents.
     *
     * @return number of duplicate documents per collection and total count
     */
    public Map<String, Integer> logDuplicates() {
        final Map<String, Integer> duplicates = new LinkedHashMap<>();
        duplicates.put("roots", logCollectionDuplicates("roots"));
        duplicates.put("persons", logCollectionDuplicates("persons"));
        duplicates.put("sources", logCollectionDuplicates("sources"));
        duplicates.put("submitters", logCollectionDuplicates("submitters"));
        final int roots = duplicates.get("roots");
        final int persons = duplicates.get("persons");
        final int sources = duplicates.get("sources");
        final int submitters = duplicates.get("submitters");
        final int total = roots + persons + sources + submitters;
        duplicates.put("total", total);
        return duplicates;
    }

    /**
     * Keep the first matching document and delete later duplicates.
     *
     * @param collectionName the Mongo collection to clean
     * @return number of deleted documents
     */
    private long cleanupCollection(final String collectionName) {
        final Map<String, List<Object>> idsByKey = groupedIdsByKey(collectionName);

        long deletedCount = 0L;
        for (final List<Object> ids : idsByKey.values()) {
            if (ids.size() <= 1) {
                continue;
            }
            // Ensure deterministic behavior: sort IDs before deciding which one to keep
            ids.sort(Comparator.comparing(Object::toString));
            final List<Object> idsToDelete = ids.subList(1, ids.size());
            final Query deleteQuery = Query.query(Criteria.where("_id").in(idsToDelete));
            final DeleteResult deleteResult =
                mongoTemplate.remove(deleteQuery, collectionName);
            deletedCount += deleteResult.getDeletedCount();
        }
        return deletedCount;
    }

    /**
     * @param collectionName the collection to scan
     * @return number of duplicate documents identified
     */
    private int logCollectionDuplicates(final String collectionName) {
        final Map<String, List<Object>> idsByKey = groupedIdsByKey(collectionName);
        int duplicateCount = 0;
        for (final Map.Entry<String, List<Object>> entry : idsByKey.entrySet()) {
            final List<Object> ids = entry.getValue();
            if (ids.size() <= 1) {
                continue;
            }
            final int duplicates = ids.size() - 1;
            duplicateCount += duplicates;
            log.warn("Duplicate group in {}: key={} ids={} duplicates={}",
                collectionName, entry.getKey(), ids, duplicates);
        }
        if (duplicateCount == 0) {
            log.info("No duplicates found in {}", collectionName);
        }
        return duplicateCount;
    }

    /**
     * @param collectionName the collection to scan
     * @return map of dedupe-key to list of document ids
     */
    private Map<String, List<Object>> groupedIdsByKey(final String collectionName) {
        final Query query = new Query();
        query.fields()
            .include("_id")
            .include("filename")
            .include("string");
        final Map<String, List<Object>> idsByKey = new LinkedHashMap<>();
        try (Stream<Document> documents =
                mongoTemplate.stream(query, Document.class, collectionName)) {
            documents.forEach(doc -> {
                final Object id = doc.get("_id");
                if (id == null) {
                    return;
                }
                final String key = buildKey(doc);
                if (key == null) {
                    log.warn("Skipping document without valid filename/string in {}: id={}",
                        collectionName, id);
                    return;
                }
                List<Object> ids = idsByKey.get(key);
                if (ids == null) {
                    final List<Object> newIds = newIdList();
                    idsByKey.put(key, newIds);
                    newIds.add(id);
                } else {
                    ids.add(id);
                }
            });
        }
        return idsByKey;
    }

    /**
     * @param doc document from a top-level GED collection
     * @return de-duplication key based on filename and string, or {@code null}
     *         if either component is missing
     */
    private String buildKey(final Document doc) {
        final Object filename = doc.get("filename");
        final Object string = doc.get("string");
        if (filename == null || string == null) {
            return null;
        }
        return String.valueOf(filename) + "\u0000" + String.valueOf(string);
    }

    /**
     * @return a mutable list for collecting document IDs
     */
    private List<Object> newIdList() {
        return new ArrayList<>();
    }
}
