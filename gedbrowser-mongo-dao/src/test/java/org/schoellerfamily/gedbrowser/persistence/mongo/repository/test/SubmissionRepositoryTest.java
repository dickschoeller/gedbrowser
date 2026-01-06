package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SubmissionRepositoryTest {
    /** */
    @Autowired
    private transient SubmissionDocumentRepositoryMongo submissionDocumentRepository;
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
    public void testSubmission() {
        final SubmissionDocument document = submissionDocumentRepository
            .findByFileAndString(root.getFilename(), "SUBMISSION");
        final Submission submission = (Submission) toObjConverter.createGedObject(root, document);
        assertEquals("SUBMISSION", submission.getString(), "Expected submission string");
    }

    /** */
    @Test
    public void testSubmissionRoot() {
        final SubmissionDocument document = submissionDocumentRepository
            .findByRootAndString(rootDocument, "SUBMISSION");
        final Submission submission = (Submission) toObjConverter.createGedObject(root, document);
        assertEquals("SUBMISSION", submission.getString(), "Expected submission string");
    }

    /** */
    @Test
    public void testBogus() {
        final SubmissionDocument perdoc = submissionDocumentRepository
            .findByFileAndString(root.getFilename(), "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    public void testBogusRoot() {
        final SubmissionDocument perdoc = submissionDocumentRepository
            .findByRootAndString(rootDocument, "Mumble");
        assertNull(perdoc, "Bogus request should return null");
    }

    /** */
    @Test
    public void testCountRoot() {
        final long expected = 1;
        final long count = submissionDocumentRepository.count(rootDocument);
        assertEquals(expected, count, "Should be 1 submission");
    }

    /** */
    @Test
    public void testCountFilename() {
        final long expected = 1;
        final long count = submissionDocumentRepository.count(rootDocument.getFilename());
        assertEquals(expected, count, "Should be 1 submission");
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<SubmissionDocument> list = submissionDocumentRepository
            .findAll(rootDocument);
        int count = 0;
        for (final SubmissionDocument submission : list) {
            checkEquals("Type string mismatch", "submission", submission.getType());
            count++;
        }
        final long expected = 1;
        assertEquals(expected, count, "Should be 1 submission");
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<SubmissionDocument> list = submissionDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final SubmissionDocument submission : list) {
            checkEquals("Type string mismatch", "submission", submission.getType());
            count++;
        }
        final long expected = 1;
        assertEquals(expected, count, "Should be 1 submission");
    }

    /** */
    @Test
    public void testLastId() {
        final String string = submissionDocumentRepository.lastId(rootDocument);
        assertEquals("SUBN", string, "");
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
