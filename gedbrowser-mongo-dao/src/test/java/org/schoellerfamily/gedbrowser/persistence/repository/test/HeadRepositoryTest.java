package org.schoellerfamily.gedbrowser.persistence.repository.test;

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
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FamilyDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    HeadDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    PersonDocumentRepository;
import org.schoellerfamily.gedbrowser.persistence.repository.
    RootDocumentRepository;
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
public final class HeadRepositoryTest {
    /** */
    private static final String HEADER_STRING = "Header";

    /** */
    @Autowired
    private transient RootDocumentRepository rootDocumentRepository;

    /** */
    @Autowired
    private transient PersonDocumentRepository personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepository familyDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepository sourceDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepository headDocumentRepository;

    /** */
    private transient Root root;

    /** */
    private transient RootDocument rootDocument;

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
            final GedDocument<?> gedDoc = GedDocumentFactory.getInstance().
                    createGedDocument(ged);
            if (gedDoc instanceof PersonDocument) {
                personDocumentRepository.save((PersonDocument) gedDoc);
            } else if (gedDoc instanceof FamilyDocument) {
                familyDocumentRepository.save((FamilyDocument) gedDoc);
            } else if (gedDoc instanceof SourceDocument) {
                sourceDocumentRepository.save((SourceDocument) gedDoc);
            } else if (gedDoc instanceof HeadDocument) {
                headDocumentRepository.save((HeadDocument) gedDoc);
            }
        }

        rootDocument = new RootDocument();
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
        final Head head = (Head) GedDocumentFactory.getInstance().
                createGedObject(root, headdoc);
        // TODO fails, should string be "Head" "Header" or blank?
        assertEquals(HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testRoot() {
        final HeadDocument headdoc = headDocumentRepository.
                findByRootAndString(rootDocument, HEADER_STRING);
        final Head head = (Head) GedDocumentFactory.getInstance().
                createGedObject(root, headdoc);
        // TODO fails, should string be "Head" "Header" or blank?
        assertEquals(HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testBogus() {
        final HeadDocument headdoc = headDocumentRepository.
                findByFileAndString(root.getFilename(), "BOGUS");
        assertNull(headdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final HeadDocument headdoc = headDocumentRepository.
                findByRootAndString(rootDocument, "BOGUS");
        assertNull(headdoc);
    }
}
