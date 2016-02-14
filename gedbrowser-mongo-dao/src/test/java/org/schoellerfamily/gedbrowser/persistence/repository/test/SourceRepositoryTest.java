package org.schoellerfamily.gedbrowser.persistence.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.repository.
    SourceDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class SourceRepositoryTest {
    /** */
    @Autowired
    private transient SourceDocumentRepository sourceDocumentRepository;

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
    public final void testSource() {
        final SourceDocument document = sourceDocumentRepository.
                findByFileAndString(root.getFilename(), "S2");
        final Source source = (Source) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals("S2", source.getString());
    }

    /** */
    @Test
    public final void testSourceRoot() {
        final SourceDocument document = sourceDocumentRepository.
                findByRootAndString(rootDocument, "S2");
        final Source source = (Source) GedDocumentFactory.getInstance().
                createGedObject(root, document);
        assertEquals("S2", source.getString());
    }

    /** */
    @Test
    public final void testBogus() {
        final SourceDocument perdoc = sourceDocumentRepository.
                findByFileAndString(root.getFilename(), "S999999");
        assertNull(perdoc);
    }

    /** */
    @Test
    public final void testBogusRoot() {
        final SourceDocument perdoc = sourceDocumentRepository.
                findByRootAndString(rootDocument, "S999999");
        assertNull(perdoc);
    }
}
