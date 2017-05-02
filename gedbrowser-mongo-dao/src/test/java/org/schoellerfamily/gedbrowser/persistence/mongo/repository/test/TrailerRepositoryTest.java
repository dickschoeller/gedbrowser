package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class TrailerRepositoryTest {
    /** */
    private static final String TRAILER_STRING = "Trailer";

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;
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
    public void testTrailer() {
        final TrailerDocument document = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), TRAILER_STRING);
        final Trailer trailer =
                (Trailer) toObjConverter.createGedObject(root, document);
        assertEquals("Expected trailer string",
                TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public void testTrailerRoot() {
        final TrailerDocument document = trailerDocumentRepository.
                findByRootAndString(rootDocument, TRAILER_STRING);
        final Trailer trailer =
                (Trailer) toObjConverter.createGedObject(root, document);
        assertEquals("Expected trailer string",
                TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByFileAndString(root.getFilename(), "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final TrailerDocument perdoc = trailerDocumentRepository.
                findByRootAndString(rootDocument, "Mumble");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should only be one trailer",
                1, trailerDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should only be one trailer",
                1,
                trailerDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<TrailerDocument> list =
                trailerDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final TrailerDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one trailer", 1, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<TrailerDocument> list =
                trailerDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final TrailerDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one trailer", 1, count);
    }
}
