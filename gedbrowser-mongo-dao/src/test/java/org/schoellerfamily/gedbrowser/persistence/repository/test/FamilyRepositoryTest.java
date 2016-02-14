package org.schoellerfamily.gedbrowser.persistence.repository.test;

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
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FamilyDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class FamilyRepositoryTest {
    /** */
    @Autowired
    private transient FamilyDocumentRepository familyDocumentRepository;

    /** */
    @Autowired
    private transient RepositoryFixture repositoryFixture;

    /** */
    private transient Root root;

    /** */
    private transient RootDocument rootDocument;

    /**
     * @throws IOException because the reader does
     */
    @Before
    public void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocument();
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
        final Family family = (Family) GedDocumentFactory.getInstance().
                createGedObject(root, famdoc);
        assertEquals("F1", family.getString());
        // TODO test following husband, wife, children
    }

    /** */
    @Test
    public void testF1Root() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByRootAndString(rootDocument, "F1");
        final Family family = (Family) GedDocumentFactory.getInstance().
                createGedObject(root, famdoc);
        assertEquals("F1", family.getString());
        // TODO test following husband, wife, children
    }

    /** */
    @Test
    public void testBogus() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByFileAndString(root.getFilename(), "F999999");
        assertNull(famdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final FamilyDocument famdoc = familyDocumentRepository.
                findByRootAndString(rootDocument, "F999999");
        assertNull(famdoc);
    }
}
