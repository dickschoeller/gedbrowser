package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SubmissionRepositoryTest {
    /** */
    @Autowired
    private transient SubmissionDocumentRepositoryMongo
        submissionDocumentRepository;
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
    @Before
    public void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocumentMongo();
        rootDocument.setFilename(root.getFilename());
        rootDocument.setDbName(root.getDbName());
        rootDocument.setGedObject(root);
    }

    /** */
    @After
    public void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    public void testSubmission() {
        final SubmissionDocument document = submissionDocumentRepository.
                findByFileAndString(root.getFilename(), "SUBMISSION");
        final Submission submission =
                (Submission) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submission string",
                "SUBMISSION", submission.getString());
    }

    /** */
    @Test
    public void testSubmissionRoot() {
        final SubmissionDocument document = submissionDocumentRepository.
                findByRootAndString(rootDocument, "SUBMISSION");
        final Submission submission =
                (Submission) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submission string",
                "SUBMISSION", submission.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final SubmissionDocument perdoc = submissionDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final SubmissionDocument perdoc = submissionDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        final long expected = 1;
        final long count = submissionDocumentRepository.count(rootDocument);
        assertEquals("Should be 1 submission", expected, count);
    }

    /** */
    @Test
    public void testCountFilename() {
        final long expected = 1;
        final long count =
                submissionDocumentRepository.count(rootDocument.getFilename());
        assertEquals("Should be 1 submission", expected, count);
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<SubmissionDocument> list =
                submissionDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SubmissionDocument submission : list) {
            checkEquals("Type string mismatch",
                    "submission", submission.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submission", expected, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<SubmissionDocument> list =
                submissionDocumentRepository.findAll(
                        rootDocument.getFilename());
        int count = 0;
        for (final SubmissionDocument submission : list) {
            checkEquals("Type string mismatch",
                    "submission", submission.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submission", expected, count);
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
        assertEquals(message, expected, actual);
    }
}
