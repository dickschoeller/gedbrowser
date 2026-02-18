package org.schoellerfamily.gedbrowser.persistence.mongo.repository.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
@SuppressWarnings({ "PMD.TooManyMethods" })
final class RepositoryFinderIT {
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
    @BeforeEach
    void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
    }

    /** */
    @AfterEach
    void tearDown() {
        repositoryFixture.clearRepository();
    }

    /** */
    @Test
    void testUntypedPerson() {
        final Person person = (Person) finder.find(root, "I1");
        assertEquals("I1", person.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testUntypedFamily() {
        final Family family = (Family) finder.find(root, "F1");
        assertEquals("F1", family.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testUntypedSource() {
        final Source source = (Source) finder.find(root, "S2");
        assertEquals("S2", source.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testUntypedSubmitter() {
        final Submitter submitter = (Submitter) finder.find(root, "SUB1");
        assertEquals("SUB1", submitter.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testUntypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING);
        assertEquals(HEADER_STRING, head.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testUntypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(root, TRAILER_STRING);
        assertEquals(TRAILER_STRING, trailer.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testUntypedNotFound() {
        final GedObject ged = finder.find(root, "Bozo");
        assertNull(ged, "Should not have found anything");
    }

    /** */
    @Test
    void testTypedPerson() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("I1", person.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testTypedFamily() {
        final Family family = (Family) finder.find(root, "F1", Family.class);
        assertEquals("F1", family.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testTypedSource() {
        final Source source = (Source) finder.find(root, "S2", Source.class);
        assertEquals("S2", source.getString(), "Mismatched ID");
    }

    /** */
    @Test
    void testTypedSubmitter() {
        final Submitter submitter = (Submitter) finder.find(root, "SUB1", Submitter.class);
        assertEquals("SUB1", submitter.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testTypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING, Head.class);
        assertEquals(HEADER_STRING, head.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testTypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(root, TRAILER_STRING, Trailer.class);
        assertEquals(TRAILER_STRING, trailer.getString(), "Mismatched tag string");
    }

    /** */
    @Test
    void testTypedNotFoundPerson() {
        final Person person = finder.find(root, "S1", Person.class);
        assertNull(person, "Should not have found anything");
    }

    /** */
    @Test
    void testTypedNotFoundFamily() {
        final Family family = finder.find(root, "I1", Family.class);
        assertNull(family, "Should not have found anything");
    }

    /** */
    @Test
    void testTypedNotFoundSource() {
        final Source source = finder.find(root, "F1", Source.class);
        assertNull(source, "Should not have found anything");
    }

    /** */
    @Test
    void testTypedNotFoundSubmitter() {
        final Submitter submitter = finder.find(root, "SUB2", Submitter.class);
        assertNull(submitter, "Should not have found anything");
    }

    /** */
    @Test
    void testTypedNotFoundHead() {
        final Head head = finder.find(root, TRAILER_STRING, Head.class);
        assertNull(head, "Should not have found anything");
    }

    @Test
    void testTypedNotFoundTrailer() {
        final Trailer trailer = finder.find(root, "T1", Trailer.class);
        assertNull(trailer, "Should not have found anything");
    }

    @Test
    void testGetFilenameFromRoot() {
        assertEquals("bigtest.ged", finder.getFilename(root), "Filename mismatch");
    }

    @Test
    void testGetFilenameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("bigtest.ged", finder.getFilename(person), "Filename mismatch");
    }

    @Test
    void testGetDbNameFromRoot() {
        assertEquals("bigtest", finder.getDbName(root), "Filename mismatch");
    }

    @Test
    void testGetDbNameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("bigtest", finder.getDbName(person), "DB name mismatch");
    }

    @Test
    void testInsertPerson() {
        final Person person = new Person(root, new ObjectId(BOGUS_ID));
        finder.insert(root, person);
        assertEquals(person, finder.find(root, BOGUS_ID, Person.class), "Person mismatch");
    }

    @Test
    void testInsertFamily() {
        final Family family = new Family(root, new ObjectId(BOGUS_ID));
        finder.insert(root, family);
        assertEquals(family, finder.find(root, BOGUS_ID, Family.class), "Family mismatch");
    }

    @Test
    void testInsertSource() {
        final Source source = new Source(root, new ObjectId(BOGUS_ID));
        finder.insert(root, source);
        assertEquals(source, finder.find(root, BOGUS_ID, Source.class), "Source mismatch");
    }

    @Test
    void testInsertSubmitter() {
        final Submitter submitter = new Submitter(root, new ObjectId(BOGUS_ID));
        finder.insert(root, submitter);
        assertEquals(submitter, finder.find(root, BOGUS_ID, Submitter.class), "Submitter mismatch");
    }

    @Test
    void testInsertHead() {
        // TODO this test describes the current, probably wrong behavior.
        final Head head = new Head(root, BOGUS_ID);
        finder.insert(root, head);
        final Head newHead = finder.find(root, BOGUS_ID, Head.class);
        assertNotEquals(head, newHead);
    }

    @Test
    void testInsertHeadTag() {
        final Head head = new Head(root, BOGUS_ID);
        finder.insert(root, head);
        final Head newHead = finder.find(root, BOGUS_ID, Head.class);
        assertEquals("Header", newHead.getString(), "Tag string mismatch");
    }

    @Test
    void testInsertTrailer() {
        final Trailer trailer = new Trailer(root, BOGUS_ID);
        finder.insert(root, trailer);
        assertEquals(trailer, finder.find(root, BOGUS_ID, Trailer.class),
            "Should have found trailer");
    }

    @Test
    void testSurnameSchoeller() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] names = { "Schoeller, John Vincent", "Schoeller, Melissa Robinson",
            "Schoeller, Richard John", "Schoeller, Vivian Grace" };
        final Collection<Person> persons = finder.findBySurname(root, "Schoeller");
        int i = 0;
        for (final Person person : persons) {
            final String indexName = person.getIndexName();
            assertEquals(names[i++], indexName, "Name mismatch");
        }
    }

    @Test
    void testSurnameNotFound() {
        final Collection<Person> persons = finder.findBySurname(root, "Mumble");
        assertEquals(0, persons.size(), "Should be empty list");
    }

    @Test
    void testSurnamesBeginWithS() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] names = { "Sacerdote", "Schoeller" };
        final Collection<String> surnames = finder.findBySurnamesBeginWith(root, "S");
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(names[i++], surname, "Surname mismatch");
        }
    }

    @Test
    void testSurnamesBeginWithQ() {
        final Collection<String> surnames = finder.findBySurnamesBeginWith(root, "Q");
        assertEquals(0, surnames.size(), "Should have no surnames with Q");
    }

    @Test
    void testSurnameInitialLetters() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] expected = { "F", "H", "K", "L", "R", "S", };
        final Collection<String> letters = finder.findSurnameInitialLetters(root);
        int i = 0;
        for (final String surname : letters) {
            assertEquals(expected[i++], surname, "Surname mismatch");
        }
    }

    @Test
    void testWithWrongGedObjectForRoot() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                finder.find(person, "foo");
            })
            .withMessage("Owner must be root");
    }

    @Test
    void testWithWrongClassForClazz() {
        assertNull(finder.find(root, BOGUS_ID, GedObject.class), "Should not have found anything");
    }

    @Test
    void testFinderFindAllPersonsCount() {
        final Collection<Person> persons = finder.find(root, Person.class);
        final int expected = 16;
        final int actual = persons.size();
        assertEquals(expected, actual, "Expected to find 16 people");
    }

    @Test
    void testFinderFindAllPersonsNotRoot() {
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        final Person person = builder.createPerson();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                finder.find(person, Person.class);
            });
    }

    @Test
    void testFinderFindAllBadClass() {
        final Collection<GedObject> find = finder.find(root, GedObject.class);
        assertNull(find, "Should be null because asking for bogus class");
    }

    @Test
    void testFinderFindAllPersons() {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final String[] names = { "I1", "I2", "I752", "I3", "I4", "I5", "I755", "I753", "I6", "I7",
            "I8", "I754", "I9", "I4248", "I10", "I5266" };
        final List<String> nameList = Arrays.asList(names);
        final Collection<Person> persons = finder.find(root, Person.class);
        for (final Person person : persons) {
            final String id = person.getString();
            assertTrue(nameList.contains(id), "Person ID (" + id + ") not found in expectations");
        }
    }
}
