package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.navigator.FamilyNavigator;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmissionDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmitterDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
@SuppressWarnings({ "PMD.ExcessiveImports" })
public final class RootRepositoryTest {
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
    @Autowired
    private transient SubmissionDocumentRepositoryMongo submissionDocumentRepository;
    /** */
    @Autowired
    private transient SubmitterDocumentRepositoryMongo submitterDocumentRepository;
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
     * @throws IOException because the reader can
     */
    @BeforeEach
    public void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
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
    void testPersons() {
        final long expectedPersonCount = 16L;
        assertEquals(expectedPersonCount, personDocumentRepository.count(), "Count mismatch");
    }

    /** */
    @Test
    void testPerson() {
        final PersonDocument perdoc = personDocumentRepository
            .findByFileAndString(root.getFilename(), "I1");
        final Person person = (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals("Melissa Robinson/Schoeller/", person.getName().getString(), "Name mismatch");
    }

    /** */
    @Test
    void testFamilies() {
        final long expectedFamilyCount = 6L;
        assertEquals(expectedFamilyCount, familyDocumentRepository.count(), "Should be 6 families");
    }

    /** */
    @Test
    void testFamilyHusband() {
        final String filename = root.getFilename();
        final FamilyDocument famdoc = familyDocumentRepository.findByFileAndString(filename, "F1");
        final Family family = (Family) toObjConverter.createGedObject(root, famdoc);
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final Husband h = navigator.getHusband();
        assertEquals("I2", h.getToString(), "ID mismatch");
    }

    /** */
    @Test
    void testFamilyWife() {
        final String filename = root.getFilename();
        final FamilyDocument famdoc = familyDocumentRepository.findByFileAndString(filename, "F1");
        final Family family = (Family) toObjConverter.createGedObject(root, famdoc);
        final FamilyNavigator navigator = new FamilyNavigator(family);
        final Wife w = navigator.getWife();
        assertEquals("I3", w.getToString(), "ID mismatch");
    }

    /** */
    @Test
    void testSources() {
        final long expectedSourceCount = 9L;
        assertEquals(expectedSourceCount, sourceDocumentRepository.count(), "Count mismatch");
    }

    /** */
    @Test
    void testSource() {
        final SourceDocument soudoc = sourceDocumentRepository
            .findByFileAndString(root.getFilename(), "S2");
        final Source source = (Source) toObjConverter.createGedObject(root, soudoc);
        assertEquals("S2", source.getString(), "ID mismatch");
    }

    /** */
    @Test
    void testRoot() {
        final RootDocument rootdoc = rootDocumentRepository.findByFileAndString(root.getFilename(),
            root.getString());
        final Root newRoot = (Root) toObjConverter.createGedObject(null, rootdoc);
        assertEquals(newRoot, root, "Should return same root");
    }

    /** */
    @Test
    void testWhole() {
        final Map<String, GedObject> map = root.getObjects();
        for (final Map.Entry<String, GedObject> entry : map.entrySet()) {
            final GedObject ged = entry.getValue();
            assertGedMatch(ged);
        }
    }

    /**
     * @param ged the item to check
     */
    private void assertGedMatch(final GedObject ged) {
        if (ged instanceof Person) {
            assertPersonMatch(ged);
        } else if (ged instanceof Source) {
            assertSourceMatch(ged);
        } else if (ged instanceof Family) {
            assertFamilyMatch(ged);
        } else if (ged instanceof Head) {
            assertHeadMatch(ged);
        } else if (ged instanceof Trailer) {
            assertTrailerMatch(ged);
        } else if (ged instanceof Submission) {
            assertSubmissionMatch(ged);
        } else if (ged instanceof Submitter) {
            assertSubmitterMatch(ged);
        }
    }

    /**
     * @param ged the item to check
     */
    private void assertPersonMatch(final GedObject ged) {
        final PersonDocument perdoc = personDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Person person = (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals(person, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertSourceMatch(final GedObject ged) {
        final SourceDocument soudoc = sourceDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Source source = (Source) toObjConverter.createGedObject(root, soudoc);
        assertEquals(source, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertFamilyMatch(final GedObject ged) {
        final FamilyDocument famdoc = familyDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Family family = (Family) toObjConverter.createGedObject(root, famdoc);
        assertEquals(family, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertHeadMatch(final GedObject ged) {
        final HeadDocument headoc = headDocumentRepository.findByFileAndString(root.getFilename(),
            ged.getString());
        final Head head = (Head) toObjConverter.createGedObject(root, headoc);
        assertEquals(head, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertTrailerMatch(final GedObject ged) {
        final TrailerDocument tradoc = trailerDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Trailer person = (Trailer) toObjConverter.createGedObject(root, tradoc);
        assertEquals(person, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertSubmissionMatch(final GedObject ged) {
        final SubmissionDocument subdoc = submissionDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Submission person = (Submission) toObjConverter.createGedObject(root, subdoc);
        assertEquals(person, ged, "wrong type");
    }

    /**
     * @param ged the item to check
     */
    private void assertSubmitterMatch(final GedObject ged) {
        final SubmitterDocument subdoc = submitterDocumentRepository
            .findByFileAndString(root.getFilename(), ged.getString());
        final Submitter person = (Submitter) toObjConverter.createGedObject(root, subdoc);
        assertEquals(person, ged, "wrong type");
    }

    /** */
    @Test
    void testBogusNameRoot() {
        final RootDocument rootdoc = rootDocumentRepository.findByFileAndString(root.getFilename(),
            "Mumbles");
        assertNull(rootdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testBogusFileRoot() {
        final RootDocument rootdoc = rootDocumentRepository.findByFileAndString("Mumbles",
            root.getString());
        assertNull(rootdoc, "Bogus request should return null");
    }

    /** */
    @Test
    void testFindByRoot() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> rootDocumentRepository.findByRootAndString(rootDocument, root.getString()));
    }

    /** */
    @Test
    void testCountRoot() {
        assertEquals(1, rootDocumentRepository.count(rootDocument), "Should only be one root");
    }

    /** */
    @Test
    void testCountFilename() {
        assertEquals(1, rootDocumentRepository.count(rootDocument.getFilename()),
            "Should only be one root");
    }

    /** */
    @Test
    void testFindAllRoot() {
        final Iterable<RootDocument> list = rootDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final RootDocument root1 : list) {
            checkEquals("Type string mismatch", "root", root1.getType());
            count++;
        }
        assertEquals(1, count, "Should only be one root");
    }

    /** */
    @Test
    void testFindAllFilename() {
        final Iterable<RootDocument> list = rootDocumentRepository
            .findAll(rootDocument.getFilename());
        int count = 0;
        for (final RootDocument root1 : list) {
            checkEquals("Type string mismatch", "root", root1.getType());
            count++;
        }
        assertEquals(1, count, "Should only be one root");
    }

    /** */
    @Test
    void testLastId() {
        final String string = rootDocumentRepository.lastId(rootDocument);
        assertEquals("", string, "");
    }

    /**
     * Wrapper for assertion to bypass PMD check.
     *
     * @param message  the identifying message for the AssertionError (null okay)
     * @param expected expected value
     * @param actual   actual value
     */
    private void checkEquals(final String message, final Object expected, final Object actual) {
        assertEquals(expected, actual, message);
    }
}
