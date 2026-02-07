package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
final class RootTest {
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
    @BeforeEach
    void setUp() {
        root = new Root("Root");
        final GedObjectBuilder builder = new GedObjectBuilder(root);
        person1 = builder.createPerson("I1", "Richard/Schoeller/");
        person2 = builder.createPerson("I2", "John/Schoeller/");
        person3 = builder.createPerson("I3", "Patricia/Hayes/");
        family = builder.createFamily("F1");
        final Family family3 = family;
        final Person person5 = person1;
        builder.addChildToFamily(family3, person5);
        final Family family1 = family;
        final Person person = person2;
        builder.addHusbandToFamily(family1, person);
        final Family family2 = family;
        final Person person4 = person2;
        builder.addWifeToFamily(family2, person4);
    }

    /** */
    @Test
    void testOneArgInsertAndFind() {
        final Root rt = new Root("Root");
        final Person person = new Person(rt, new ObjectId("I2"));
        rt.insert(person);
        final Name name2 = new Name(person, "John/Schoeller/");
        person.insert(name2);
        final Person personx = new Person(root, new ObjectId("I3"));
        personx.insert(new Name(personx, "Patricia/Hayes/"));
        assertEquals(person, root.find("I2"), "Person mismatch");
    }

    /** */
    @Test
    void testFindI1() {
        assertEquals(person1, root.find("I1"), "Person mismatch");
    }

    /** */
    @Test
    void testFindI2() {
        assertEquals(person2, root.find("I2"), "Person mismatch");
    }

    /** */
    @Test
    void testFindI3() {
        assertEquals(person3, root.find("I3"), "Person mismatch");
    }

    /** */
    @Test
    void testFindF1() {
        assertEquals(family, root.find("F1"), "Family mismatch");
    }

    /** */
    @Test
    void testGetObjects() {
        final Person person4 = new Person(root, new ObjectId("I4"));
        root.insert(person4);
        final Person person5 = new Person(root, new ObjectId("SQUIRT"));
        root.insert(person5);
        final Map<String, GedObject> objects = root.getObjects();
        assertTrue(OBJECT_COUNT == objects.size() && objects.keySet().contains("I1")
            && objects.keySet().contains("I2") && objects.keySet().contains("I3")
            && objects.keySet().contains("I4") && objects.keySet().contains("SQUIRT")
            && objects.keySet().contains("F1"), "Content mismatch");
    }

    /** */
    @Test
    void testGetObjectsNull() {
        final Root localRoot = new Root();
        localRoot.insert(null);
        final Map<String, GedObject> objects = root.getObjects();
        assertFalse(objects.isEmpty(), "Null object should not be inserted");
    }

    /** */
    @Test
    void testGetFilenameInitiallyNull() {
        assertNull(root.getFilename(), "Filename should initially be null");
    }

    /** */
    @Test
    void testSetGetFilename() {
        root.setFilename("foo.ged");
        assertEquals("foo.ged", root.getFilename(), "Filename should be set");
    }

    /** */
    @Test
    void testGetDbNameInitiallyNull() {
        assertNull(root.getDbName(), "DB name should initially be null");
    }

    /** */
    @Test
    void testSetGetDbName() {
        root.setDbName("foo");
        assertEquals("foo", root.getDbName(), "DB name should be set");
    }

    /** */
    @Test
    void testIndexInitiallyEmpty() {
        final Index index = root.getIndex();
        assertEquals(0, index.surnameCount(), "Index should be empty");
    }

    /** */
    @Test
    void testIndex() {
        final Index index = root.getIndex();
        root.initIndex();
        final Set<String> npsSchoeller = index.getNamesPerSurname("Schoeller");
        final Set<String> npsHayes = index.getNamesPerSurname("Hayes");
        final Set<String> nullish = index.getNamesPerSurname("Mumble");
        assertTrue(2 == index.surnameCount() && 2 == npsSchoeller.size() && 1 == npsHayes.size()
            && nullish.isEmpty(), "Counts don't match");
    }
}
