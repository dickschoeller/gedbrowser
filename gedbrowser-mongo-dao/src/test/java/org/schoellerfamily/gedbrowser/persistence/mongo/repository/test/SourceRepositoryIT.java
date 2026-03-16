package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
final class SourceRepositoryIT {
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
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;

    /** */
    private transient Root root;

    /** */
    private transient RootDocumentMongo rootDocument;

    @BeforeEach
    void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocumentMongo();
        rootDocument.setFilename(root.getFilename());
        rootDocument.setDbName(root.getDbName());
        rootDocument.setGedObject(root);
    }

    @AfterEach
    void tearDown() {
        repositoryFixture.clearRepository();
    }

    @Test
    void testSource() {
        final SourceDocument document = sourceDocumentRepository
            .findByFileAndString(root.getFilename(), "S2");
        final Source source = (Source) toObjConverter.createGedObject(root, document);
        assertEquals("S2", source.getString(), "Id mismatch");
    }

    @Test
    void testSourceRoot() {
        final SourceDocument document = sourceDocumentRepository.findByRootAndString(rootDocument,
            "S2");
        final Source source = (Source) toObjConverter.createGedObject(root, document);
        assertEquals("S2", source.getString(), "Id mismatch");
    }

    @Test
    void testBogus() {
        final SourceDocument perdoc = sourceDocumentRepository
            .findByFileAndString(root.getFilename(), "S999999");
        assertNull(perdoc, "Bogus request should return null");
    }

    @Test
    void testBogusRoot() {
        final SourceDocument perdoc = sourceDocumentRepository.findByRootAndString(rootDocument,
            "S999999");
        assertNull(perdoc, "Bogus request should return null");
    }

    @Test
    void testCountRoot() {
        assertEquals(SOURCE_COUNT, sourceDocumentRepository.count(rootDocument),
            "Should be 9 sources");
    }

    @Test
    void testCountFilename() {
        assertEquals(SOURCE_COUNT, sourceDocumentRepository.count(rootDocument.getFilename()),
            "Should be 9 sources");
    }

    @Test
    void testFindAllRoot() {
        final Iterable<SourceDocument> list = sourceDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final SourceDocument source : list) {
            checkEquals("Type string mismatch", "source", source.getType());
            count++;
        }
        assertEquals(SOURCE_COUNT, count, "Should be 9 sources");
    }

    @Test
    void testFindAllFilename() {
        final Iterable<SourceDocument> list = sourceDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final SourceDocument source : list) {
            checkEquals("Type string mismatch", "source", source.getType());
            count++;
        }
        assertEquals(SOURCE_COUNT, count, "Should be 9 sources");
    }

    @Test
    void testLastId() {
        final String string = sourceDocumentRepository.lastId(rootDocument);
        assertEquals("S229", string, "");
    }

    @Test
    void testNewId() {
        final String string = sourceDocumentRepository.newId(rootDocument);
        assertEquals("S230", string, "");
    }

    private void checkEquals(final String message, final Object expected, final Object actual) {
        assertEquals(expected, actual, message);
    }
}
