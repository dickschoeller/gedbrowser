package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class SourceRepositoryTest {
    /**
     * Expected value in source count tests.
     */
    private static final long SOURCE_COUNT = 9L;

    /** */
    @Autowired
    private transient SourceDocumentRepositoryMongo sourceDocumentRepository;

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
    public void testSource() {
        final SourceDocument document = sourceDocumentRepository.
                findByFileAndString(root.getFilename(), "S2");
        final Source source = (Source) GedDocumentMongoFactory.getInstance().
                createGedObject(root, document);
        assertEquals("Id mismatch", "S2", source.getString());
    }

    /** */
    @Test
    public void testSourceRoot() {
        final SourceDocument document = sourceDocumentRepository.
                findByRootAndString(rootDocument, "S2");
        final Source source = (Source) GedDocumentMongoFactory.getInstance().
                createGedObject(root, document);
        assertEquals("Id mismatch", "S2", source.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final SourceDocument perdoc = sourceDocumentRepository.
                findByFileAndString(root.getFilename(), "S999999");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final SourceDocument perdoc = sourceDocumentRepository.
                findByRootAndString(rootDocument, "S999999");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should be 9 sources", SOURCE_COUNT,
                sourceDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should be 9 sources", SOURCE_COUNT,
                sourceDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<SourceDocument> list =
                sourceDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SourceDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should be 9 sources", SOURCE_COUNT, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<SourceDocument> list =
                sourceDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final SourceDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should be 9 sources", SOURCE_COUNT, count);
    }
}
