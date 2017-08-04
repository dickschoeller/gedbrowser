package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmitterDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SubmitterRepositoryTest {
    /** */
    @Autowired
    private transient SubmitterDocumentRepositoryMongo
        submitterDocumentRepository;
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
    public void testSubmitter() {
        final SubmitterDocument document = submitterDocumentRepository.
                findByFileAndString(root.getFilename(), "SUB1");
        final Submitter submitter =
                (Submitter) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submitter string",
                "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testSubmitterRoot() {
        final SubmitterDocument document = submitterDocumentRepository.
                findByRootAndString(rootDocument, "SUB1");
        final Submitter submitter =
                (Submitter) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submitter string",
                "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final SubmitterDocument perdoc = submitterDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final SubmitterDocument perdoc = submitterDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        final long expected = 1;
        final long count = submitterDocumentRepository.count(rootDocument);
        assertEquals("Should be 1 submitter", expected, count);
    }

    /** */
    @Test
    public void testCountFilename() {
        final long expected = 1;
        final long count =
                submitterDocumentRepository.count(rootDocument.getFilename());
        assertEquals("Should be 1 submitter", expected, count);
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<SubmitterDocument> list =
                submitterDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SubmitterDocument submitter : list) {
            checkEquals("Type string mismatch",
                    "submitter", submitter.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submitter", expected, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<SubmitterDocument> list =
                submitterDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final SubmitterDocument submitter : list) {
            checkEquals("Type string mismatch",
                    "submitter", submitter.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submitter", expected, count);
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
