package org.schoellerfamily.gedbrowser.persistence.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.repository.
    TrailerDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class TrailerRepositoryTest {
    /** */
    private static final String TRAILER_STRING = "Trailer";

    /** */
    @Autowired
    private transient TrailerDocumentRepository trailerDocumentRepository;

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
    public final void testTrailer() {
        final TrailerDocument document = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), TRAILER_STRING);
        final Trailer trailer = (Trailer) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals(TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public final void testTrailerRoot() {
        final TrailerDocument document = trailerDocumentRepository.
                findByRootAndString(rootDocument, TRAILER_STRING);
        final Trailer trailer = (Trailer) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals(TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public final void testBogus() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull(perdoc);
    }

    /** */
    @Test
    public final void testBogusRoot() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull(perdoc);
    }


}
