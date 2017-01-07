package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.dao.mongo.TestDataReader;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class HeadRepositoryTest {
    /** */
    private static final String HEADER_STRING = "Header";

    /** */
    @Autowired
    private transient RootDocumentRepositoryMongo rootDocumentRepository;

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
    private transient Root root;

    /** */
    private transient RootDocumentMongo rootDocument;

    /**
     * @throws IOException because the reader does
     */
    @Before
    public void setUp() throws IOException {
        rootDocumentRepository.deleteAll();
        personDocumentRepository.deleteAll();
        familyDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();

        root = (Root) TestDataReader.getInstance().readBigTestSource();
        root.setFilename("bigtest");
        final Map<String, GedObject> map = root.getObjects();
        for (final GedObject ged : map.values()) {
            final GedDocument<?> gedDoc = GedDocumentMongoFactory.getInstance().
                    createGedDocument(ged);
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
    @After
    public void tearDown() {
        rootDocumentRepository.deleteAll();
        personDocumentRepository.deleteAll();
        familyDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();
        headDocumentRepository.deleteAll();
    }

    /** */
    @Test
    public void test() {
        final HeadDocument headdoc = headDocumentRepository.
                findByFileAndString(root.getFilename(), HEADER_STRING);
        final Head head = (Head) GedDocumentMongoFactory.getInstance().
                createGedObject(root, headdoc);
        // TODO fails, should string be "Head" "Header" or blank?
        assertEquals("Expected header string",
                HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testRoot() {
        final HeadDocument headdoc = headDocumentRepository.
                findByRootAndString(rootDocument, HEADER_STRING);
        final Head head = (Head) GedDocumentMongoFactory.getInstance().
                createGedObject(root, headdoc);
        // TODO fails, should string be "Head" "Header" or blank?
        assertEquals("Expected header string",
                HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final HeadDocument headdoc = headDocumentRepository.
                findByFileAndString(root.getFilename(), "BOGUS");
        assertNull("Bogus request should return null", headdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final HeadDocument headdoc = headDocumentRepository.
                findByRootAndString(rootDocument, "BOGUS");
        assertNull("Bogus request should return null", headdoc);
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should only be one head",
                1, headDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should only be one head",
                1,
                headDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<HeadDocument> list =
                headDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final HeadDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one head", 1, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<HeadDocument> list =
                headDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final HeadDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one head", 1, count);
    }
}
