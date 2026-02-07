package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.NoteDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
class NoteRepositoryIT {
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
    @BeforeEach
    void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocumentMongo();
        rootDocument.setFilename(root.getFilename());
        rootDocument.setDbName(root.getDbName());
        rootDocument.setGedObject(root);
    }

    /** */
    @AfterEach
    void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    void testN1() {
        final NoteDocument famdoc = noteDocumentRepository.findByFileAndString(root.getFilename(),
            "N1");
        final Note note = (Note) toObjConverter.createGedObject(root, famdoc);
        assertEquals("N1", note.getString(), "Id mismatch");
    }

    /** */
    @Test
    void testN1Root() {
        final NoteDocument famdoc = noteDocumentRepository.findByRootAndString(rootDocument, "N1");
        final Note note = (Note) toObjConverter.createGedObject(root, famdoc);
        assertEquals("N1", note.getString(), "Id mismatch");
    }

    /** */
    @Test
    void testBogus() {
        final NoteDocument famdoc = noteDocumentRepository.findByFileAndString(root.getFilename(),
            "N999999");
        assertNull(famdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testBogusRoot() {
        final NoteDocument famdoc = noteDocumentRepository.findByRootAndString(rootDocument,
            "N999999");
        assertNull(famdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testCountRoot() {
        assertEquals(NOTE_COUNT, noteDocumentRepository.count(rootDocument), "Should be 3 notes");
    }

    /** */
    @Test
    void testCountFilename() {
        assertEquals(NOTE_COUNT, noteDocumentRepository.count(rootDocument.getFilename()),
            "Should be 3 notes");
    }

    /** */
    @Test
    void testFindAllRoot() {
        final Iterable<NoteDocument> list = noteDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final NoteDocument note : list) {
            checkEquals("Type string mismatch", "note", note.getType());
            count++;
        }
        assertEquals(NOTE_COUNT, count, "Should be 3 notes");
    }

    /** */
    @Test
    void testFindAllFilename() {
        final Iterable<NoteDocument> list = noteDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final NoteDocument note : list) {
            checkEquals("Type string mismatch", "note", note.getType());
            count++;
        }
        assertEquals(NOTE_COUNT, count, "Should be 3 notes");
    }

    /** */
    @Test
    void testLastId() {
        final String string = noteDocumentRepository.lastId(rootDocument);
        assertEquals("N3", string, "");
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
