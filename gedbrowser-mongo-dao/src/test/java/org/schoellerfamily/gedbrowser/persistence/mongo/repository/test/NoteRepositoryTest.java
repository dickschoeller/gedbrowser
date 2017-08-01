package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.NoteDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class NoteRepositoryTest {
    /**
     * Standard answer in the note counts tests.
     */
    private static final long NOTE_COUNT = 3L;

    /** */
    @Autowired
    private transient NoteDocumentRepositoryMongo noteDocumentRepository;
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
    public void testN1() {
        final NoteDocument famdoc = noteDocumentRepository.
                findByFileAndString(root.getFilename(), "N1");
        final Note note =
                (Note) toObjConverter.createGedObject(root, famdoc);
        assertEquals("Id mismatch", "N1", note.getString());
    }

    /** */
    @Test
    public void testN1Root() {
        final NoteDocument famdoc = noteDocumentRepository.
                findByRootAndString(rootDocument, "N1");
        final Note note =
                (Note) toObjConverter.createGedObject(root, famdoc);
        assertEquals("Id mismatch", "N1", note.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final NoteDocument famdoc = noteDocumentRepository.
                findByFileAndString(root.getFilename(), "N999999");
        assertNull("Bogus request should return null", famdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final NoteDocument famdoc = noteDocumentRepository.
                findByRootAndString(rootDocument, "N999999");
        assertNull("Bogus request should return null", famdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should be 3 notes", NOTE_COUNT,
                noteDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should be 3 notes", NOTE_COUNT,
                noteDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<NoteDocument> list =
                noteDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final NoteDocument note : list) {
            checkEquals("Type string mismatch", "note", note.getType());
            count++;
        }
        assertEquals("Should be 3 notes", NOTE_COUNT, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<NoteDocument> list =
                noteDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final NoteDocument note : list) {
            checkEquals("Type string mismatch", "note", note.getType());
            count++;
        }
        assertEquals("Should be 3 notes", NOTE_COUNT, count);
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
