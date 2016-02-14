package org.schoellerfamily.gedbrowser.persistence.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SubmittorDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class SubmittorRepositoryTest {
    /** */
    private static final String SUBMITTOR_STRING = "Submittor";

    /** */
    @Autowired
    private transient SubmittorDocumentRepository
        submittorDocumentRepository; // NOPMD

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
    public final void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocument();
        rootDocument.setFilename(root.getFilename());
        rootDocument.setDbName(root.getDbName());
        rootDocument.setGedObject(root);
    }

    /** */
    @After
    public final void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    public final void testSubmittor() {
        final SubmittorDocument document = submittorDocumentRepository.
                findByFileAndString(root.getFilename(), SUBMITTOR_STRING);
        final Submittor submittor =
                (Submittor) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals(SUBMITTOR_STRING, submittor.getString());
    }

    /** */
    @Test
    public final void testSubmittorRoot() {
        final SubmittorDocument document = submittorDocumentRepository.
                findByRootAndString(rootDocument, SUBMITTOR_STRING);
        final Submittor submittor =
                (Submittor) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals(SUBMITTOR_STRING, submittor.getString());
    }

    /** */
    @Test
    public final void testBogus() {
        final SubmittorDocument perdoc = submittorDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull(perdoc);
    }

    /** */
    @Test
    public final void testBogusRoot() {
        final SubmittorDocument perdoc = submittorDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull(perdoc);
    }
}
