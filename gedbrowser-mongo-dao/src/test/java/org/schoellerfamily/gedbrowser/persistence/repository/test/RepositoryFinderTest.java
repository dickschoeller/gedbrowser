package org.schoellerfamily.gedbrowser.persistence.repository.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.fixture.RepositoryFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
public class RepositoryFinderTest { // NOPMD
    /** */
    private static final String BOGUS_ID = "XYZZY";

    /** */
    private static final String TRAILER_STRING = "Trailer";

    /** */
    private static final String HEADER_STRING = "Header";

    /** */
    private static final String SUBMITTOR_STRING = "Submittor";

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
    public final void setUp() throws IOException {
        root = repositoryFixture.loadRepository();
    }

    /** */
    @Test
    public final void testUntypedPerson() {
        final Person person = (Person) finder.find(root, "I1");
        assertEquals("I1", person.getString());
    }

    /** */
    @Test
    public final void testUntypedFamily() {
        final Family family = (Family) finder.find(root, "F1");
        assertEquals("F1", family.getString());
    }

    /** */
    @Test
    public final void testUntypedSource() {
        final Source source = (Source) finder.find(root, "S2");
        assertEquals("S2", source.getString());
    }

    /** */
    @Test
    public final void testUntypedSubmittor() {
        final Submittor submittor =
                (Submittor) finder.find(root, SUBMITTOR_STRING);
        assertEquals(SUBMITTOR_STRING, submittor.getString());
    }

    /** */
    @Test
    public final void testUntypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING);
        assertEquals(HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public final void testUntypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(root, TRAILER_STRING);
        assertEquals(TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public final void testUntypedNotFound() {
        final GedObject ged = finder.find(root, "Bozo");
        assertNull(ged);
    }

    /** */
    @Test
    public final void testTypedPerson() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("I1", person.getString());
    }

    /** */
    @Test
    public final void testTypedFamily() {
        final Family family = (Family) finder.find(root, "F1", Family.class);
        assertEquals("F1", family.getString());
    }

    /** */
    @Test
    public final void testTypedSource() {
        final Source source = (Source) finder.find(root, "S2", Source.class);
        assertEquals("S2", source.getString());
    }

    /** */
    @Test
    public final void testTypedSubmittor() {
        final Submittor submittor = (Submittor) finder.find(
                root, SUBMITTOR_STRING, Submittor.class);
        assertEquals(SUBMITTOR_STRING, submittor.getString());
    }

    /** */
    @Test
    public final void testTypedHead() {
        final Head head = (Head) finder.find(root, HEADER_STRING, Head.class);
        assertEquals(HEADER_STRING, head.getString());
    }

    /** */
    @Test
    public final void testTypedTrailer() {
        final Trailer trailer = (Trailer) finder.find(
                root, TRAILER_STRING, Trailer.class);
        assertEquals(TRAILER_STRING, trailer.getString());
    }

    /** */
    @Test
    public final void testTypedNotFoundPerson() {
        final Person person = (Person) finder.find(root, "S1", Person.class);
        assertNull(person);
    }

    /** */
    @Test
    public final void testTypedNotFoundFamily() {
        final Family family = (Family) finder.find(root, "I1", Family.class);
        assertNull(family);
    }

    /** */
    @Test
    public final void testTypedNotFoundSource() {
        final Source source = (Source) finder.find(root, "F1", Source.class);
        assertNull(source);
    }

    /** */
    @Test
    public final void testTypedNotFoundSubmittor() {
        final Submittor submittor = (Submittor) finder.find(
                root, "SUB1", Submittor.class);
        assertNull(submittor);
    }

    /** */
    @Test
    public final void testTypedNotFoundHead() {
        final Head head = (Head) finder.find(root, TRAILER_STRING, Head.class);
        assertNull(head);
    }

    /** */
    @Test
    public final void testTypedNotFoundTrailer() {
        final Trailer trailer = (Trailer) finder.find(
                root, "T1", Trailer.class);
        assertNull(trailer);
    }

    /** */
    @Test
    public final void testGetFilenameFromRoot() {
        assertEquals("bigtest.ged", finder.getFilename(root));
    }

    /** */
    @Test
    public final void testGetFilenameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("bigtest.ged", finder.getFilename(person));
    }

    /** */
    @Test
    public final void testGetDbNameFromRoot() {
        assertEquals("bigtest", finder.getDbName(root));
    }

    /** */
    @Test
    public final void testGetDbNameFromNotRoot() {
        final Person person = (Person) finder.find(root, "I1", Person.class);
        assertEquals("bigtest", finder.getDbName(person));
    }

    /** */
    @Test
    public final void testInsertPerson() {
        final Person person = new Person(root);
        person.setString(BOGUS_ID);
        finder.insert(root, person);
        assertEquals(person, finder.find(root, BOGUS_ID, Person.class));
    }

    /** */
    @Test
    public final void testInsertFamily() {
        final Family family = new Family(root);
        family.setString(BOGUS_ID);
        finder.insert(root, family);
        assertEquals(family, finder.find(root, BOGUS_ID, Family.class));
    }

    /** */
    @Test
    public final void testInsertSource() {
        final Source source = new Source(root);
        source.setString(BOGUS_ID);
        finder.insert(root, source);
        assertEquals(source, finder.find(root, BOGUS_ID, Source.class));
    }

    /** */
    @Test
    public final void testInsertSubmittor() {
        final Submittor submittor = new Submittor(root);
        submittor.setString(BOGUS_ID);
        finder.insert(root, submittor);
        assertEquals(submittor, finder.find(root, BOGUS_ID, Submittor.class));
    }

    /** */
    @Test
    public final void testInsertHead() {
        // TODO this test describes the current, probably wrong behavior.
        final Head head = new Head(root);
        head.setString(BOGUS_ID);
        finder.insert(root, head);
        final Head newHead = finder.find(root, BOGUS_ID, Head.class);
        assertNotEquals(head, newHead);
        assertEquals("Header", newHead.getString());
    }

    /** */
    @Test
    public final void testInsertTrailer() {
        final Trailer trailer = new Trailer(root);
        trailer.setString(BOGUS_ID);
        finder.insert(root, trailer);
        assertEquals(trailer, finder.find(root, BOGUS_ID, Trailer.class));
    }

    /** */
    @Test
    public final void testSurnameSchoeller() {
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
            assertEquals(names[i++], indexName);
        }
    }

    /** */
    @Test
    public final void testSurnameNotFound() {
        final Collection<Person> persons =
                finder.findBySurname(root, "Mumble");
        assertEquals(0, persons.size());
    }

    /** */
    @Test
    public final void testSurnamesBeginWithS() {
        final String[] names = {
                "Sacerdote",
                "Schoeller"
        };
        final Collection<String> surnames =
                finder.findBySurnamesBeginWith(root, "S");
        int i = 0;
        for (final String surname : surnames) {
            assertEquals(names[i++], surname);
        }
    }

    /** */
    @Test
    public final void testSurnamesBeginWithQ() {
        final Collection<String> surnames =
                finder.findBySurnamesBeginWith(root, "Q");
        assertEquals(0, surnames.size());
    }

    /** */
    @Test
    public final void testSurnameInitialLetters() {
        final String[] expected = {
                "F", "H", "K", "L", "R", "S",
        };
        final Collection<String> letters =
                finder.findSurnameInitialLetters(root);
        int i = 0;
        for (final String surname : letters) {
            assertEquals(expected[i++], surname);
        }
    }

    /** */
    @Test
    public final void testWithWrongGedObjectForRoot() {
        try {
            finder.find(new Person(), "foo");
            fail("Expected an exception here");
        } catch (IllegalArgumentException e) {
            assertEquals("Owner must be root", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testWithWrongClassForClazz() {
        assertNull(finder.find(root, BOGUS_ID, GedObject.class));
    }
}
