package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.persistence.mongo.fixture.RepositoryFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public final class RepositoryFinderTest {
    /** */
    private static final String BOGUS_ID = "XYZZY";

    /** */
    private static final String TRAILER_STRING = "Trailer";

    /** */
    private static final String HEADER_STRING = "Header";

    /** */
    @Autowired
    private transient FinderStrategy finder;

    /** */
    @Autowired
    private transient RepositoryFixture repositoryFixture;

    /** */
    private transient Root root;

    /**
     * @throws IOException because the reader can
     */
    @Before
    public void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
    }

    /** */
    @After
    public void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    public void testUntypedPerson() {
        final Person person = (Person) finder.find(root, "I1");
        assertEquals("Mismatched ID", "I1", person.getString());
    }

    /** */
    @Test
    public void testUntypedFamily() {
        final Family family = (Family) finder.find(root, "F1");
        assertEquals("Mismatched ID", "F1", family.getString());
    }

    /** */
    @Test
    public void testUntypedSource() {
        final Source source = (Source) finder.find(root, "S2");
        assertEquals("Mismatched ID", "S2", source.getString());
    }

    /** */
    @Test
    public void testUntypedSubmitter() {
        final Submitter submitter =
                (Submitter) finder.find(root, "SUB1");
        assertEquals("Mismatched tag string",
                "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testUntypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING);
        assertEquals("Mismatched tag string",
                HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testUntypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(root, TRAILER_STRING);
        assertEquals("Mismatched tag string",
                TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public void testUntypedNotFound() {
        final GedObject ged = finder.find(root, "Bozo");
        assertNull("Should not have found anything", ged);
    }

    /** */
    @Test
    public void testTypedPerson() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("Mismatched ID", "I1", person.getString());
    }

    /** */
    @Test
    public void testTypedFamily() {
        final Family family = (Family) finder.find(root, "F1", Family.class);
        assertEquals("Mismatched ID", "F1", family.getString());
    }

    /** */
    @Test
    public void testTypedSource() {
        final Source source = (Source) finder.find(root, "S2", Source.class);
        assertEquals("Mismatched ID", "S2", source.getString());
    }

    /** */
    @Test
    public void testTypedSubmitter() {
        final Submitter submitter =
                (Submitter) finder.find(root, "SUB1", Submitter.class);
        assertEquals("Mismatched tag string", "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testTypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING, Head.class);
        assertEquals("Mismatched tag string",
                HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public void testTypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(
                root, TRAILER_STRING, Trailer.class);
        assertEquals("Mismatched tag string",
                TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public void testTypedNotFoundPerson() {
        final Person person = finder.find(root, "S1", Person.class);
        assertNull("Should not have found anything", person);
    }

    /** */
    @Test
    public void testTypedNotFoundFamily() {
        final Family family = finder.find(root, "I1", Family.class);
        assertNull("Should not have found anything", family);
    }

    /** */
    @Test
    public void testTypedNotFoundSource() {
        final Source source = finder.find(root, "F1", Source.class);
        assertNull("Should not have found anything", source);
    }

    /** */
    @Test
    public void testTypedNotFoundSubmitter() {
        final Submitter submitter = finder.find(root, "SUB2", Submitter.class);
        assertNull("Should not have found anything", submitter);
    }

    /** */
    @Test
    public void testTypedNotFoundHead() {
        final Head head = finder.find(root, TRAILER_STRING, Head.class);
        assertNull("Should not have found anything", head);
    }

    /** */
    @Test
    public void testTypedNotFoundTrailer() {
        final Trailer trailer = finder.find(root, "T1", Trailer.class);
        assertNull("Should not have found anything", trailer);
    }

    /** */
    @Test
    public void testGetFilenameFromRoot() {
        assertEquals("Filename mismatch",
                "bigtest.ged", finder.getFilename(root));
    }

    /** */
    @Test
    public void testGetFilenameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("Filename mismatch",
                "bigtest.ged", finder.getFilename(person));
    }

    /** */
    @Test
    public void testGetDbNameFromRoot() {
        assertEquals("Filename mismatch",
                "bigtest", finder.getDbName(root));
    }

    /** */
    @Test
    public void testGetDbNameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("DB name mismatch",
                "bigtest", finder.getDbName(person));
    }

    /** */
    @Test
    public void testInsertPerson() {
        final Person person = new Person(root, new ObjectId(BOGUS_ID));
        finder.insert(root, person);
        assertEquals("Person mismatch",
                person, finder.find(root, BOGUS_ID, Person.class));
    }

    /** */
    @Test
    public void testInsertFamily() {
        final Family family = new Family(root, new ObjectId(BOGUS_ID));
        finder.insert(root, family);
        assertEquals("Family mismatch",
                family, finder.find(root, BOGUS_ID, Family.class));
    }

    /** */
    @Test
    public void testInsertSource() {
        final Source source = new Source(root, new ObjectId(BOGUS_ID));
        finder.insert(root, source);
        assertEquals("Source mismatch",
                source, finder.find(root, BOGUS_ID, Source.class));
    }

    /** */
    @Test
    public void testInsertSubmitter() {
        final Submitter submitter = new Submitter(root, new ObjectId(BOGUS_ID));
        finder.insert(root, submitter);
        assertEquals("Submitter mismatch",
                submitter, finder.find(root, BOGUS_ID, Submitter.class));
    }

    /** */
    @Test
    public void testInsertHead() {
        // TODO this test describes the current, probably wrong behavior.
        final Head head = new Head(root, BOGUS_ID);
        finder.insert(root, head);
        final Head newHead = finder.find(root, BOGUS_ID, Head.class);
        assertNotEquals(head, newHead);
    }

    /** */
    @Test
    public void testInsertHeadTag() {
        final Head head = new Head(root, BOGUS_ID);
        finder.insert(root, head);
        final Head newHead = finder.find(root, BOGUS_ID, Head.class);
        assertEquals("Tag string mismatch", "Header", newHead.getString());
    }

    /** */
    @Test
    public void testInsertTrailer() {
        final Trailer trailer = new Trailer(root, BOGUS_ID);
        finder.insert(root, trailer);
        assertEquals("Should have found trailer",
                trailer, finder.find(root, BOGUS_ID, Trailer.class));
    }

    /** */
    @Test
    public void testSurnameSchoeller() {
        final String[] names = {
                "Schoeller, John Vincent",
                "Schoeller, Melissa Robinson",
                "Schoeller, Richard John",
                "Schoeller, Vivian Grace"
        };
        final Collection<Person> persons =
                finder.findBySurname(root, "Schoeller");
        int i = 0;
        for (final Person person : persons) {
            final String indexName = person.getIndexName();
            assertEquals("Name mismatch", names[i++], indexName);
        }
    }

    /** */
    @Test
    public void testSurnameNotFound() {
        final Collection<Person> persons =
                finder.findBySurname(root, "Mumble");
        assertEquals("Should be empty list",
                0, persons.size());
    }

    /** */
    @Test
    public void testSurnamesBeginWithS() {
        final String[] names = {
                "Sacerdote",
                "Schoeller"
        };
        final Collection<String> surnames =
                finder.findBySurnamesBeginWith(root, "S");
        int i = 0;
        for (final String surname : surnames) {
            assertEquals("Surname mismatch", names[i++], surname);
        }
    }

    /** */
    @Test
    public void testSurnamesBeginWithQ() {
        final Collection<String> surnames =
                finder.findBySurnamesBeginWith(root, "Q");
        assertEquals("Should have no surnames with Q",
                0, surnames.size());
    }

    /** */
    @Test
    public void testSurnameInitialLetters() {
        final String[] expected = {
                "F", "H", "K", "L", "R", "S",
        };
        final Collection<String> letters =
                finder.findSurnameInitialLetters(root);
        int i = 0;
        for (final String surname : letters) {
            assertEquals("Surname mismatch", expected[i++], surname);
        }
    }

    /** */
    @Test
    public void testWithWrongGedObjectForRoot() {
        try {
            final GedObjectBuilder builder = new GedObjectBuilder();
            finder.find(builder.createPerson(), "foo");
            fail("Expected an exception here");
        } catch (IllegalArgumentException e) {
            assertEquals("Message mismatch",
                    "Owner must be root", e.getMessage());
        }
    }

    /** */
    @Test
    public void testWithWrongClassForClazz() {
        assertNull("Should not have found anything",
                finder.find(root, BOGUS_ID, GedObject.class));
    }

    /** */
    @Test
    public void testFinderFindAllPersonsCount() {
        final Collection<Person> persons = finder.find(root, Person.class);
        final int expected = 16;
        final int actual = persons.size();
        assertEquals("Expected to find 16 people", expected, actual);
    }

    /** */
    @Test
    public void testFinderFindAllPersonsNotRoot() {
        try {
            final GedObjectBuilder builder = new GedObjectBuilder(root);
            final Person person = builder.createPerson();
            finder.find(person, Person.class);
            fail("Should ahve thrown an exception");
        } catch (IllegalArgumentException e) {
            // This is a pass.
        }
    }

    /** */
    @Test
    public void testFinderFindAllBadClass() {
        final Collection<GedObject> find = finder.find(root, GedObject.class);
        assertNull("Should be null because asking for bogus class", find);
    }

    /** */
    @Test
    public void testFinderFindAllPersons() {
        final String[] names = {
                "I1", "I2", "I752", "I3", "I4", "I5", "I755", "I753",
                "I6", "I7", "I8", "I754", "I9", "I4248", "I10", "I5266" };
        final List<String> nameList = Arrays.asList(names);
        final Collection<Person> persons = finder.find(root, Person.class);
        for (final Person person : persons) {
            final String id = person.getString();
            assertTrue("Person ID (" + id + ") not found in expectations",
                    nameList.contains(id));
        }
    }
}
