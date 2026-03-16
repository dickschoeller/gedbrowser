package org.schoellerfamily.gedbrowser.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.stream.Stream;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.api.endpoint.DuplicateCleanupService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;

/**
 * Tests for {@link DuplicateCleanupService}.
 */
final class DuplicateCleanupServiceTest {

    @Test
    void testCleanupDeletesDuplicatesAndAggregatesCounts() {
        final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
        final DuplicateCleanupService service = new DuplicateCleanupService(mongoTemplate);

        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("roots")))
            .thenReturn(Stream.of(
                doc("r2", "schoeller.ged", "Root"),
                doc("r1", "schoeller.ged", "Root")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("persons")))
            .thenReturn(Stream.of(
                doc("p1", "schoeller.ged", "I1"),
                doc("p2", "schoeller.ged", "I2")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("sources")))
            .thenReturn(Stream.of(
                doc("s1", "schoeller.ged", "S1"),
                doc("s2", "schoeller.ged", "S1"),
                doc("s3", "schoeller.ged", "S1")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("submitters")))
            .thenReturn(Stream.of(
                doc("sub1", "schoeller.ged", "SUB1")));

        when(mongoTemplate.remove(any(Query.class), eq("roots")))
            .thenReturn(DeleteResult.acknowledged(1));
        when(mongoTemplate.remove(any(Query.class), eq("sources")))
            .thenReturn(DeleteResult.acknowledged(2));

        final Map<String, Integer> result = service.cleanup();

        assertEquals(1, result.get("roots"));
        assertEquals(0, result.get("persons"));
        assertEquals(2, result.get("sources"));
        assertEquals(0, result.get("submitters"));
        assertEquals(3, result.get("total"));
        verify(mongoTemplate).remove(any(Query.class), eq("roots"));
        verify(mongoTemplate, never()).remove(any(Query.class), eq("persons"));
        verify(mongoTemplate).remove(any(Query.class), eq("sources"));
        verify(mongoTemplate, never()).remove(any(Query.class), eq("submitters"));
    }

    @Test
    void testLogDuplicatesReportsCountsWithoutDeleting() {
        final MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
        final DuplicateCleanupService service = new DuplicateCleanupService(mongoTemplate);

        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("roots")))
            .thenReturn(Stream.of(
                doc("r1", "schoeller.ged", "Root"),
                doc("r2", "schoeller.ged", "Root")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("persons")))
            .thenReturn(Stream.of(
                doc("p1", "schoeller.ged", "I1"),
                docMissingString("p2", "schoeller.ged"),
                docMissingId("schoeller.ged", "I1"),
                doc("p3", "schoeller.ged", "I1")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("sources")))
            .thenReturn(Stream.of(doc("s1", "schoeller.ged", "S1")));
        when(mongoTemplate.stream(any(Query.class), eq(Document.class), eq("submitters")))
            .thenReturn(Stream.of(
                doc("sub1", "schoeller.ged", "SUB1"),
                doc("sub2", "schoeller.ged", "SUB1")));

        final Map<String, Integer> result = service.logDuplicates();

        assertEquals(1, result.get("roots"));
        assertEquals(1, result.get("persons"));
        assertEquals(0, result.get("sources"));
        assertEquals(1, result.get("submitters"));
        assertEquals(3, result.get("total"));
        verify(mongoTemplate, never()).remove(any(Query.class), any(String.class));
    }

    private Document doc(final String id, final String filename, final String string) {
        return new Document("_id", id)
            .append("filename", filename)
            .append("string", string);
    }

    private Document docMissingId(final String filename, final String string) {
        return new Document("filename", filename)
            .append("string", string);
    }

    private Document docMissingString(final String id, final String filename) {
        return new Document("_id", id)
            .append("filename", filename);
    }
}
