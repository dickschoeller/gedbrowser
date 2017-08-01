package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmittorDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SubmittorRepositoryTest {
    /** */
    @Autowired
    private transient SubmittorDocumentRepositoryMongo
        submittorDocumentRepository;
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
    public void testSubmittor() {
        final SubmittorDocument document = submittorDocumentRepository.
                findByFileAndString(root.getFilename(), "SUB1");
        final Submittor submittor =
                (Submittor) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submittor string",
                "SUB1", submittor.getString());
    }

    /** */
    @Test
    public void testSubmittorRoot() {
        final SubmittorDocument document = submittorDocumentRepository.
                findByRootAndString(rootDocument, "SUB1");
        final Submittor submittor =
                (Submittor) toObjConverter.createGedObject(root, document);
        assertEquals("Expected submittor string",
                "SUB1", submittor.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final SubmittorDocument perdoc = submittorDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final SubmittorDocument perdoc = submittorDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        final long expected = 1;
        final long count = submittorDocumentRepository.count(rootDocument);
        assertEquals("Should be 1 submittor", expected, count);
    }

    /** */
    @Test
    public void testCountFilename() {
        final long expected = 1;
        final long count =
                submittorDocumentRepository.count(rootDocument.getFilename());
        assertEquals("Should be 1 submittor", expected, count);
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<SubmittorDocument> list =
                submittorDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SubmittorDocument submittor : list) {
            checkEquals("Type string mismatch",
                    "submittor", submittor.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submittor", expected, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<SubmittorDocument> list =
                submittorDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final SubmittorDocument submittor : list) {
            checkEquals("Type string mismatch",
                    "submittor", submittor.getType());
            count++;
        }
        final long expected = 1;
        assertEquals("Should be 1 submittor", expected, count);
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
