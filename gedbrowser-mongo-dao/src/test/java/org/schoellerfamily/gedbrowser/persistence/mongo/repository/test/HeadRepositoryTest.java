package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class HeadRepositoryTest {
    /** */
    private static final String HEADER_STRING = "Header";

    /** */
    @Autowired
    private transient PersonDocumentRepositoryMongo personDocumentRepository;
    /** */
    @Autowired
    private transient FamilyDocumentRepositoryMongo familyDocumentRepository;
    /** */
    @Autowired
    private transient SourceDocumentRepositoryMongo sourceDocumentRepository;
    /** */
    @Autowired
    private transient HeadDocumentRepositoryMongo headDocumentRepository;
    /** */
    @Autowired
    private transient RepositoryFixture repositoryFixture;
    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient GedDocumentMongoToGedObjectConverter toObjConverter;
    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private transient Root root;

    /** */
    private transient RootDocumentMongo rootDocument;

    /**
     * @throws IOException because the reader does
     */
    @BeforeEach
    public void setUp() throws IOException {
        repositoryFixture.clearRepository();

        root = reader.readBigTestSource();
        root.setFilename("bigtest");
        final Map<String, GedObject> map = root.getObjects();
        for (final GedObject ged : map.values()) {
            final GedDocument<?> gedDoc = toDocConverter.createGedDocument(ged);
            if (gedDoc instanceof PersonDocumentMongo) {
                personDocumentRepository.save((PersonDocumentMongo) gedDoc);
            } else if (gedDoc instanceof FamilyDocumentMongo) {
                familyDocumentRepository.save((FamilyDocumentMongo) gedDoc);
            } else if (gedDoc instanceof SourceDocumentMongo) {
                sourceDocumentRepository.save((SourceDocumentMongo) gedDoc);
            } else if (gedDoc instanceof HeadDocumentMongo) {
                headDocumentRepository.save((HeadDocumentMongo) gedDoc);
            }
        }

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
    void test() {
        final HeadDocument headdoc = headDocumentRepository.findByFileAndString(root.getFilename(),
            HEADER_STRING);
        final Head head = (Head) toObjConverter.createGedObject(root, headdoc);
        assertEquals(HEADER_STRING, head.getString(), "Expected header string");
    }

    /** */
    @Test
    void testRoot() {
        final HeadDocument headdoc = headDocumentRepository.findByRootAndString(rootDocument,
            HEADER_STRING);
        final Head head = (Head) toObjConverter.createGedObject(root, headdoc);
        assertEquals(HEADER_STRING, head.getString(), "Expected header string");
    }

    /** */
    @Test
    void testBogus() {
        final HeadDocument headdoc = headDocumentRepository.findByFileAndString(root.getFilename(),
            "BOGUS");
        assertNull(headdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testBogusRoot() {
        final HeadDocument headdoc = headDocumentRepository.findByRootAndString(rootDocument,
            "BOGUS");
        assertNull(headdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testCountRoot() {
        assertEquals(1, headDocumentRepository.count(rootDocument), "Should only be one head");
    }

    /** */
    @Test
    void testCountFilename() {
        assertEquals(1, headDocumentRepository.count(rootDocument.getFilename()),
            "Should only be one head");
    }

    /** */
    @Test
    void testFindAllRoot() {
        final Iterable<HeadDocument> list = headDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final HeadDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals(1, count, "Should only be one head");
    }

    /** */
    @Test
    void testFindAllFilename() {
        final Iterable<HeadDocument> list = headDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final HeadDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals(1, count, "Should only be one head");
    }

    /** */
    @Test
    void testLastId() {
        final String string = headDocumentRepository.lastId(rootDocument);
        assertEquals("", string, "");
    }
}
