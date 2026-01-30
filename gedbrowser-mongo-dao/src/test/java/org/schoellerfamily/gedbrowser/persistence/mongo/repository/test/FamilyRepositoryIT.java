package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class FamilyRepositoryIT {
    /**
     * Standard answer in the family counts tests.
     */
    private static final long FAMILY_COUNT = 6L;

    /** */
    @Autowired
    private transient FamilyDocumentRepositoryMongo familyDocumentRepository;
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
    void testF1() {
        final FamilyDocument famdoc = familyDocumentRepository
            .findByFileAndString(root.getFilename(), "F1");
        final Family family = (Family) toObjConverter.createGedObject(root, famdoc);
        assertEquals("F1", family.getString(), "Id mismatch");
    }

    /** */
    @Test
    void testF1Root() {
        final FamilyDocument famdoc = familyDocumentRepository.findByRootAndString(rootDocument,
            "F1");
        final Family family = (Family) toObjConverter.createGedObject(root, famdoc);
        assertEquals("F1", family.getString(), "Id mismatch");
    }

    /** */
    @Test
    void testBogus() {
        final FamilyDocument famdoc = familyDocumentRepository
            .findByFileAndString(root.getFilename(), "F999999");
        assertNull(famdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testBogusRoot() {
        final FamilyDocument famdoc = familyDocumentRepository.findByRootAndString(rootDocument,
            "F999999");
        assertNull(famdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testCountRoot() {
        assertEquals(FAMILY_COUNT, familyDocumentRepository.count(rootDocument),
            "Should be 6 families");
    }

    /** */
    @Test
    void testCountFilename() {
        assertEquals(FAMILY_COUNT, familyDocumentRepository.count(rootDocument.getFilename()),
            "Should be 6 families");
    }

    /** */
    @Test
    void testFindAllRoot() {
        final Iterable<FamilyDocument> list = familyDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final FamilyDocument family : list) {
            checkEquals("Type string mismatch", "family", family.getType());
            count++;
        }
        assertEquals(FAMILY_COUNT, count, "Should be 6 families");
    }

    /** */
    @Test
    void testFindAllFilename() {
        final Iterable<FamilyDocument> list = familyDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final FamilyDocument family : list) {
            checkEquals("Type string mismatch", "family", family.getType());
            count++;
        }
        assertEquals(FAMILY_COUNT, count, "Should be 6 families");
    }

    /** */
    @Test
    void testLastId() {
        final String string = familyDocumentRepository.lastId(rootDocument);
        assertEquals("F10", string, "");
    }

    /** */
    @Test
    void testNewId() {
        final String string = familyDocumentRepository.newId(rootDocument);
        assertEquals("F11", string, "");
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
