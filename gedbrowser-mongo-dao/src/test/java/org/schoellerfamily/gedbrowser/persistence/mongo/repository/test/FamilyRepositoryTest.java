package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class FamilyRepositoryTest {
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
    public void testF1() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByFileAndString(root.getFilename(), "F1");
        final Family family = (Family) GedDocumentMongoFactory.getInstance().
                createGedObject(root, famdoc);
        assertEquals("Id mismatch", "F1", family.getString());
        // TODO test following husband, wife, children
    }

    /** */
    @Test
    public void testF1Root() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByRootAndString(rootDocument, "F1");
        final Family family = (Family) GedDocumentMongoFactory.getInstance().
                createGedObject(root, famdoc);
        assertEquals("Id mismatch", "F1", family.getString());
        // TODO test following husband, wife, children
    }

    /** */
    @Test
    public void testBogus() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByFileAndString(root.getFilename(), "F999999");
        assertNull("Bogus request should return null", famdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByRootAndString(rootDocument, "F999999");
        assertNull("Bogus request should return null", famdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should be 6 families", FAMILY_COUNT,
                familyDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should be 6 families", FAMILY_COUNT,
                familyDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<FamilyDocument> list =
                familyDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final FamilyDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should be 6 families", FAMILY_COUNT, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<FamilyDocument> list =
                familyDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final FamilyDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should be 6 families", FAMILY_COUNT, count);
    }
}
