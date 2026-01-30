package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmitterDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SubmitterRepositoryIT {
    /** */
    @Autowired
    private transient SubmitterDocumentRepositoryMongo submitterDocumentRepository;
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
    void testSubmitter() {
        final SubmitterDocument document = submitterDocumentRepository
            .findByFileAndString(root.getFilename(), "SUB1");
        final Submitter submitter = (Submitter) toObjConverter.createGedObject(root, document);
        assertEquals("SUB1", submitter.getString(), "Expected submitter string");
    }

    /** */
    @Test
    void testSubmitterRoot() {
        final SubmitterDocument document = submitterDocumentRepository
            .findByRootAndString(rootDocument, "SUB1");
        final Submitter submitter = (Submitter) toObjConverter.createGedObject(root, document);
        assertEquals("SUB1", submitter.getString(), "Expected submitter string");
    }

    /** */
    @Test
    void testBogus() {
        final SubmitterDocument perdoc = submitterDocumentRepository
            .findByFileAndString(root.getFilename(), "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testBogusRoot() {
        final SubmitterDocument perdoc = submitterDocumentRepository
            .findByRootAndString(rootDocument, "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testCountRoot() {
        final long expected = 1;
        final long count = submitterDocumentRepository.count(rootDocument);
        assertEquals(expected, count, "Should be 1 submitter");
    }

    /** */
    @Test
    void testCountFilename() {
        final long expected = 1;
        final long count = submitterDocumentRepository.count(rootDocument.getFilename());
        assertEquals(expected, count, "Should be 1 submitter");
    }

    /** */
    @Test
    void testFindAllRoot() {
        final Iterable<SubmitterDocument> list = submitterDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SubmitterDocument submitter : list) {
            checkEquals("Type string mismatch", "submitter", submitter.getType());
            count++;
        }
        final long expected = 1;
        assertEquals(expected, count, "Should be 1 submitter");
    }

    /** */
    @Test
    void testFindAllFilename() {
        final Iterable<SubmitterDocument> list = submitterDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final SubmitterDocument submitter : list) {
            checkEquals("Type string mismatch", "submitter", submitter.getType());
            count++;
        }
        final long expected = 1;
        assertEquals(expected, count, "Should be 1 submitter");
    }

    /** */
    @Test
    void testLastId() {
        final String string = submitterDocumentRepository.lastId(rootDocument);
        assertEquals("SUB1", string, "");
    }

    /**
     * Wrapper for assertion to bypass PMD check.
     *
     * @param message  the identifying message for the AssertionError (null okay)
     * @param expected expected value
     * @param actual   actual value
     */
    private void checkEquals(final String message, final Object expected, final Object actual) {
        assertEquals(expected, actual, message);
    }
}
