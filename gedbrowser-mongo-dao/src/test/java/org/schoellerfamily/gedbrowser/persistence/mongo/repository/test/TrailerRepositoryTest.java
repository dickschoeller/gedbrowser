package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class TrailerRepositoryTest {
    /** */
    private static final String TRAILER_STRING = "Trailer";

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;
    /** */
    @Autowired
    private transient RepositoryFixture repositoryFixture;
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /** */
    private transient Root root;

    /** */
    private transient RootDocumentMongo rootDocument;

    /**
     * @throws IOException because the reader does
     */
    @BeforeEach
    public void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocumentMongo();
        rootDocument.setFilename(root.getFilename());
        rootDocument.setDbName(root.getDbName());
        rootDocument.setGedObject(root);
    }

    /** */
    @AfterEach
    public void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    public void testTrailer() {
        final TrailerDocument document = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), TRAILER_STRING);
        final Trailer trailer =
                (Trailer) toObjConverter.createGedObject(root, document);
        assertEquals(TRAILER_STRING, trailer.getString(), "Expected trailer string");
    }

    /** */
    @Test
    public void testTrailerRoot() {
        final TrailerDocument document = trailerDocumentRepository.
                findByRootAndString(rootDocument, TRAILER_STRING);
        final Trailer trailer =
                (Trailer) toObjConverter.createGedObject(root, document);
        assertEquals(TRAILER_STRING, trailer.getString(), "Expected trailer string");
    }

    /** */
    @Test
    public void testBogus() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    public void testBogusRoot() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals(1, trailerDocumentRepository.count(rootDocument), "Should only be one trailer");
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals(1, trailerDocumentRepository.count(rootDocument.getFilename()), "Should only be one trailer");
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<TrailerDocument> list =
                trailerDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final TrailerDocument trailer : list) {
            checkEquals("Type string mismatch", "trailer", trailer.getType());
            count++;
        }
        assertEquals(1, count, "Should only be one trailer");
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<TrailerDocument> list =
                trailerDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final TrailerDocument trailer : list) {
            checkEquals("Type string mismatch", "trailer", trailer.getType());
            count++;
        }
        assertEquals(1, count, "Should only be one trailer");
    }

    /**
     * Wrapper for assertion to bypass PMD check.
     *
     * @param message the identifying message for the AssertionError (null okay)
     * @param expected expected value
     * @param actual actual value
     */
    private void checkEquals(final String message, final Object expected,
            final Object actual) {
        assertEquals(expected, actual, message);
    }
}