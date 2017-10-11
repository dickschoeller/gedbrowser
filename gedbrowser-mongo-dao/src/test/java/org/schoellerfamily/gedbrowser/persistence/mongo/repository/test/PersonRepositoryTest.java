package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class PersonRepositoryTest {
    /**
     * Normal expected value in person counting tests.
     */
    private static final long PERSON_COUNT = 16L;

    /** */
    @Autowired
    private transient PersonDocumentRepositoryMongo personDocumentRepository;
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
    public void testMelissa() {
        final PersonDocument perdoc = personDocumentRepository.
                findByFileAndString(root.getFilename(), "I1");
        final Person person =
                (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals("Name mistmatch",
                "Melissa Robinson/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testMelissaRoot() {
        final PersonDocument perdoc = personDocumentRepository.
                findByRootAndString(rootDocument, "I1");
        final Person person =
                (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals("Name mistmatch",
                "Melissa Robinson/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testDick() {
        final PersonDocument perdoc = personDocumentRepository.
                findByFileAndString(root.getFilename(), "I2");
        final Person person =
                (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals("Name mistmatch",
                "Richard John/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testDickRoot() {
        final PersonDocument perdoc = personDocumentRepository.
                findByRootAndString(rootDocument, "I2");
        final Person person =
                (Person) toObjConverter.createGedObject(root, perdoc);
        assertEquals("Name mistmatch",
                "Richard John/Schoeller/", person.getName().getString());
    }

    /** */
    @Test
    public void testSchoellerRoot() {
        final String[] names = {
                "Schoeller, John Vincent",
                "Schoeller, Melissa Robinson",
                "Schoeller, Richard John",
                "Schoeller, Vivian Grace"
        };
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurname(rootDocument, "Schoeller");
        int i = 0;
        for (final PersonDocument perdoc : perdocs) {
            assertEquals("Name mistmatch",
                    names[i++], perdoc.getIndexName());
        }
    }

    /** */
    @Test
    public void testRootNullSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurname(rootDocument, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullRootWithSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurname(null, "Schoeller");
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullRootNullSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurname(null, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testFileNullSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurname(root.getFilename(), null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullFileWithSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurname(null, "Schoeller");
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullFileNullSurname() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurname(null, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testSchoeller() {
        final String[] names = {
                "Schoeller, John Vincent",
                "Schoeller, Melissa Robinson",
                "Schoeller, Richard John",
                "Schoeller, Vivian Grace"
        };
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurname(root.getFilename(), "Schoeller");
        int i = 0;
        for (final PersonDocument perdoc : perdocs) {
            assertEquals("Name mismatch",
                    names[i++], perdoc.getIndexName());
        }
    }

    /** */
    @Test
    public void testS() {
        final String[] names = {
                "Sacerdote, David Andrew",
                "Sacerdote, George Steven",
                "Sacerdote, Michael George",
                "Schoeller, John Vincent",
                "Schoeller, Melissa Robinson",
                "Schoeller, Richard John",
                "Schoeller, Vivian Grace"
        };
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurnameBeginsWith(root.getFilename(), "S");
        int i = 0;
        for (final PersonDocument perdoc : perdocs) {
            assertEquals("Name mismatch",
                    names[i++], perdoc.getIndexName());
        }
    }

    /** */
    @Test
    public void testSRoot() {
        final String[] names = {
                "Sacerdote, David Andrew",
                "Sacerdote, George Steven",
                "Sacerdote, Michael George",
                "Schoeller, John Vincent",
                "Schoeller, Melissa Robinson",
                "Schoeller, Richard John",
                "Schoeller, Vivian Grace"
        };
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurnameBeginsWith(rootDocument, "S");
        int i = 0;
        for (final PersonDocument perdoc : perdocs) {
            assertEquals("Name mismatch",
                    names[i++], perdoc.getIndexName());
        }
    }

    /** */
    @Test
    public void testBogus() {
        final PersonDocument perdoc = personDocumentRepository.
                findByFileAndString(root.getFilename(), "I999999");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testBogusRoot() {
        final PersonDocument perdoc = personDocumentRepository.
                findByRootAndString(rootDocument, "I999999");
        assertNull("Bogus request should return null", perdoc);
    }

    /** */
    @Test
    public void testFindByFileNotFound() {
        final Iterable<PersonDocument> persons = personDocumentRepository
                .findAll("XYZZY");
        assertEquals("Bogus request should return empty",
                0, count(persons));
    }

    /** */
    @Test
    public void testFindByFileNull() {
        final Iterable<PersonDocument> persons = personDocumentRepository
                .findAll((String) null);
        assertEquals("Bogus request should return empty",
                0, count(persons));
    }

    /** */
    @Test
    public void testFindByNullRoot() {
        final Iterable<PersonDocument> persons = personDocumentRepository
                .findAll((RootDocument) null);
        assertEquals("Bogus request should return empty",
                0, count(persons));
    }

    /** */
    @Test
    public void testFindByRootWithFileNotFound() {
        final RootDocumentMongo rootDocument1 = new RootDocumentMongo();
        rootDocument1.setFilename("XYZZY.ged");
        final Iterable<PersonDocument> persons = personDocumentRepository
                .findAll(rootDocument1);
        assertEquals("Bogus request should return empty",
                0, count(persons));
    }

    /** */
    @Test
    public void testRootNullSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurnameBeginsWith(rootDocument, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullRootWithSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurnameBeginsWith(null, "S");
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullRootNullSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByRootAndSurnameBeginsWith(null, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testFileNullSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurnameBeginsWith(root.getFilename(), null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullFileWithSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurnameBeginsWith(null, "S");
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testNullFileNullSurnameBegins() {
        final Collection<PersonDocument> perdocs = personDocumentRepository
                .findByFileAndSurnameBeginsWith(null, null);
        assertEquals("Bogus request should return empty",
                0, perdocs.size());
    }

    /** */
    @Test
    public void testCountRoot() {
        final long count = personDocumentRepository.count(rootDocument);
        assertEquals("Should be 16 persons", PERSON_COUNT, count);
    }

    /** */
    @Test
    public void testCountFilename() {
        final long count =
                personDocumentRepository.count(rootDocument.getFilename());
        assertEquals("Should be 16 persons", PERSON_COUNT, count);
    }

    /** */
    @Test
    public void testFindAllRoot() {
        final Iterable<PersonDocument> list =
                personDocumentRepository.findAll(rootDocument);
        int count = 0;
        for (final PersonDocument person : list) {
            checkEquals("Type string mismatch", "person", person.getType());
            count++;
        }
        assertEquals("Should be 16 persons", PERSON_COUNT, count);
    }

    /** */
    @Test
    public void testFindAllFilename() {
        final Iterable<PersonDocument> list =
                personDocumentRepository.findAll(rootDocument.getFilename());
        int count = 0;
        for (final PersonDocument person : list) {
            checkEquals("Type string mismatch", "person", person.getType());
            count++;
        }
        assertEquals("Should be 16 persons", PERSON_COUNT, count);
    }

    /** */
    @Test
    public void testLastId() {
        final String string = personDocumentRepository.lastId(rootDocument);
        assertEquals("", "I5266", string);
    }

    /** */
    @Test
    public void testNewId() {
        final String string = personDocumentRepository.newId(rootDocument);
        assertEquals("", "I5267", string);
    }

    /**
     * Wrapper for assertion to bypass PMD check.
     *
     * @param message the identifying message for the AssertionError (null okay)
     * @param expected expected value
     * @param actual actual value
     */
    private void checkEquals(final String message, final Object expected,
            final Object actual) {
        assertEquals(message, expected, actual);
    }

    /**
     * Count the number of items in an collection.
     *
     * @param iterable the collect expressed as an iterable
     * @return the count
     */
    private int count(final Iterable<?> iterable) {
        final Iterator<?> iterator = iterable.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            count++;
            iterator.next();
        }
        return count;
    }
}
