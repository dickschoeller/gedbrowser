package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmittorDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
@SuppressWarnings({ "PMD.ExcessiveImports" })
public class RootRepositoryTest {
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
    private transient SubmittorDocumentRepositoryMongo
        submittorDocumentRepository; // NOPMD

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;

    /** */
    @Autowired
    private transient RepositoryFixture repositoryFixture;

    /** */
    private transient Root root;

    /** */
    private transient RootDocumentMongo rootDocument;

    /**
     * @throws IOException because the reader can
     */
    @Before
    public final void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
        rootDocument = new RootDocumentMongo();
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
    public final void testPersons() {
        final long expectedPersonCount = 16L;
        assertEquals("Count mismatch",
                expectedPersonCount, personDocumentRepository.count());
    }

    /** */
    @Test
    public final void testPerson() {
        final PersonDocument perdoc = personDocumentRepository.
                findByFileAndString(root.getFilename(), "I1");
        final Person person = (Person) GedDocumentMongoFactory.getInstance().
                createGedObject(root, perdoc);
        assertEquals("Name mismatch",
                "Melissa Robinson/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public final void testFamilies() {
        final long expectedFamilyCount = 6L;
        assertEquals("Count mismatch",
                expectedFamilyCount, familyDocumentRepository.count());
    }

    /** */
    @Test
    public final void testFamily() {
        final String filename = root.getFilename();
        final FamilyDocument famdoc = familyDocumentRepository.
                findByFileAndString(filename, "F1");
        final Family family = (Family) GedDocumentMongoFactory.getInstance().
                createGedObject(root, famdoc);
        assertEquals("ID mismatch",
                "I2", family.findHusband().getToString());
    }

    /** */
    @Test
    public final void testSources() {
        final long expectedSourceCount = 9L;
        assertEquals("Count mismatch",
                expectedSourceCount, sourceDocumentRepository.count());
    }

    /** */
    @Test
    public final void testSource() {
        final SourceDocument soudoc = sourceDocumentRepository.
                findByFileAndString(root.getFilename(), "S2");
        final Source source = (Source) GedDocumentMongoFactory.getInstance().
                createGedObject(root, soudoc);
        assertEquals("ID mismatch", "S2", source.getString());
    }

    /** */
    @Test
    public final void testRoot() {
        final RootDocument rootdoc =
                rootDocumentRepository.findByFileAndString(
                        root.getFilename(), root.getString());
        final Root newRoot =
                (Root) GedDocumentMongoFactory.getInstance().createGedObject(
                        null, rootdoc);
        assertEquals("Should return same root", newRoot, root);
    }

    /** */
    @Test
    public final void testWhole() {
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
        } else if (ged instanceof Submittor) {
            assertSubmittorMatch(ged);
        }
    }

    /**
     * @param ged the item to check
     */
    private void assertPersonMatch(final GedObject ged) {
        final PersonDocument perdoc = personDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Person person =
                (Person) GedDocumentMongoFactory.getInstance()
                        .createGedObject(root, perdoc);
        assertEquals("wrong type", person, ged);
    }

    /**
     * @param ged the item to check
     */
    private void assertSourceMatch(final GedObject ged) {
        final SourceDocument soudoc = sourceDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Source source =
                (Source) GedDocumentMongoFactory.getInstance()
                        .createGedObject(root, soudoc);
        assertEquals("wrong type", source, ged);
    }

    /**
     * @param ged the item to check
     */
    private void assertFamilyMatch(final GedObject ged) {
        final FamilyDocument famdoc = familyDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Family family =
                (Family) GedDocumentMongoFactory.getInstance()
                        .createGedObject(root, famdoc);
        assertEquals("wrong type", family, ged);
    }

    /**
     * @param ged the item to check
     */
    private void assertHeadMatch(final GedObject ged) {
        final HeadDocument headoc = headDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Head head = (Head) GedDocumentMongoFactory.getInstance()
                .createGedObject(root, headoc);
        assertEquals("wrong type", head, ged);
    }

    /**
     * @param ged the item to check
     */
    private void assertTrailerMatch(final GedObject ged) {
        final TrailerDocument tradoc = trailerDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Trailer person = (Trailer) GedDocumentMongoFactory.
                getInstance().createGedObject(root, tradoc);
        assertEquals("wrong type", person, ged);
    }

    /**
     * @param ged the item to check
     */
    private void assertSubmittorMatch(final GedObject ged) {
        final SubmittorDocument subdoc = submittorDocumentRepository
                .findByFileAndString(root.getFilename(),
                        ged.getString());
        final Submittor person = (Submittor) GedDocumentMongoFactory.
                getInstance().createGedObject(root, subdoc);
        assertEquals("wrong type", person, ged);
    }

    /** */
    @Test
    public final void testBogusNameRoot() {
        final RootDocument rootdoc =
                rootDocumentRepository.findByFileAndString(
                        root.getFilename(), "Mumbles");
        assertNull("Bogus request should return null", rootdoc);
    }

    /** */
    @Test
    public final void testBogusFileRoot() {
        final RootDocument rootdoc =
                rootDocumentRepository.findByFileAndString(
                        "Mumbles", root.getString());
        assertNull("Bogus request should return null", rootdoc);
    }

    /** */
    @Test(expected = IllegalArgumentException.class)
    public final void testFindByRoot() {
        rootDocumentRepository.findByRootAndString(
                rootDocument, root.getString());
        fail("should not get here");
    }

    /** */
    @Test
    public void testCountRoot() {
        assertEquals("Should only be one root",
                1, rootDocumentRepository.count(rootDocument));
    }

    /** */
    @Test
    public void testCountFilename() {
        assertEquals("Should only be one root",
                1,
                rootDocumentRepository.count(rootDocument.getFilename()));
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<RootDocument> list =
                rootDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final RootDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one root", 1, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<RootDocument> list =
                rootDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final RootDocument trailer : list) {
            trailer.getType();
            count++;
        }
        assertEquals("Should only be one root", 1, count);
    }
}
