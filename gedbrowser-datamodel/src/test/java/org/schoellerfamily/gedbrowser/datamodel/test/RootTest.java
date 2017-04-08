package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Index;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class RootTest {
    /** */
    private static final int OBJECT_COUNT = 6;
    /** */
    private transient Root root;
    /** */
    private transient Person person1;
    /** */
    private transient Person person2;
    /** */
    private transient Person person3;
    /** */
    private transient Family family;

    /** */
    @Before
    public void setUp() {
        root = new Root("Root");
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        person1 = builder.getPersonBuilder().createPerson("I1",
                "Richard/Schoeller/");
        person2 = builder.getPersonBuilder().createPerson("I2",
                "John/Schoeller/");
        person3 = builder.getPersonBuilder().createPerson("I3",
                "Patricia/Hayes/");
        family = builder.getFamilyBuilder().createFamily("F1");
        final Family family3 = family;
        final Person person5 = person1;
        builder.getFamilyBuilder().addChildToFamily(family3, person5);
        final Family family1 = family;
        final Person person = person2;
        builder.getFamilyBuilder().addHusbandToFamily(family1, person);
        final Family family2 = family;
        final Person person4 = person2;
        builder.getFamilyBuilder().addWifeToFamily(family2, person4);
    }

    /** */
    @Test
    public void testOneArgInsertAndFind() {
        final Root rt = new Root("Root");
        final Person person = new Person(rt, new ObjectId("I2"));
        rt.insert(person);
        final Name name2 = new Name(person, "John/Schoeller/");
        person.insert(name2);
        final Person personx = new Person(root, new ObjectId("I3"));
        personx.insert(new Name(personx, "Patricia/Hayes/"));
        assertEquals("Person mismatch", person, root.find("I2"));
    }

    /** */
    @Test
    public void testFindI1() {
        assertEquals("Person mismatch", person1, root.find("I1"));
    }

    /** */
    @Test
    public void testFindI2() {
        assertEquals("Person mismatch", person2, root.find("I2"));
    }

    /** */
    @Test
    public void testFindI3() {
        assertEquals("Person mismatch", person3, root.find("I3"));
    }

    /** */
    @Test
    public void testFindF1() {
        assertEquals("Family mismatch", family, root.find("F1"));
    }

    /** */
    @Test
    public void testGetObjects() {
        final Person person4 = new Person(root, new ObjectId("I4"));
        root.insert(person4);
        final Person person5 = new Person(root, new ObjectId("SQUIRT"));
        root.insert(person5);
        final Map<String, GedObject> objects = root.getObjects();
        assertTrue("Content mismatch",
                OBJECT_COUNT == objects.size()
                        && objects.keySet().contains("I1")
                        && objects.keySet().contains("I2")
                        && objects.keySet().contains("I3")
                        && objects.keySet().contains("I4")
                        && objects.keySet().contains("SQUIRT")
                        && objects.keySet().contains("F1"));
    }

    /** */
    @Test
    public void testGetObjectsNull() {
        final Root localRoot = new Root();
        localRoot.insert(null);
        final Map<String, GedObject> objects = root.getObjects();
        assertFalse("Null object should not be inserted", objects.isEmpty());
    }

    /** */
    @Test
    public void testGetFilenameInitiallyNull() {
        assertNull("Filename should initially be null", root.getFilename());
    }

    /** */
    @Test
    public void testSetGetFilename() {
        root.setFilename("foo.ged");
        assertEquals("Filename should be set", "foo.ged", root.getFilename());
    }

    /** */
    @Test
    public void testGetDbNameInitiallyNull() {
        assertNull("DB name should initially be null", root.getDbName());
    }

    /** */
    @Test
    public void testSetGetDbName() {
        root.setDbName("foo");
        assertEquals("DB name should be set", "foo", root.getDbName());
    }

    /** */
    @Test
    public void testIndexInitiallyEmpty() {
        final Index index = root.getIndex();
        assertEquals("Index should be empty", 0, index.surnameCount());
    }

    /** */
    @Test
    public void testIndex() {
        final Index index = root.getIndex();
        root.initIndex();
        final Set<String> npsSchoeller = index.getNamesPerSurname("Schoeller");
        final Set<String> npsHayes = index.getNamesPerSurname("Hayes");
        final Set<String> nullish = index.getNamesPerSurname("Mumble");
        assertTrue("Counts don't match",
                2 == index.surnameCount() && 2 == npsSchoeller.size()
                        && 1 == npsHayes.size() && nullish.isEmpty());
    }
}
