package org.schoellerfamily.gedbrowser.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Dick Schoeller
 */
public final class RootTest {
    /** */
    private static final int OBJECT_COUNT = 6;
    /** */
    private final transient Root root = new Root(null, "Root");
    /** */
    private final transient Person person1 = new Person(root,
            new ObjectId("I1"));
    /** */
    private final transient Name name1 =
            new Name(person1, "Richard/Schoeller/");
    /** */
    private final transient Person person2 = new Person(root,
            new ObjectId("I2"));
    /** */
    private final transient Name name2 = new Name(person2, "John/Schoeller/");
    /** */
    private final transient Person person3 = new Person(root,
            new ObjectId("I3"));
    /** */
    private final transient Name name3 = new Name(person3, "Patricia/Hayes/");
    /** */
    private final transient Family family = new Family(root,
            new ObjectId("F1"));
    /** */
    private final transient FamC famC = new FamC(person1, "FAMC",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS2 = new FamS(person2, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient FamS famS3 = new FamS(person3, "FAMS",
            new ObjectId("F1"));
    /** */
    private final transient Child child = new Child(family, "Child",
            new ObjectId("I1"));
    /** */
    private final transient Husband husband = new Husband(family, "Husband",
            new ObjectId("I2"));
    /** */
    private final transient Wife wife = new Wife(family, "Wife",
            new ObjectId("I3"));

    /** */
    @Before
    public void setUp() {
        root.insert(null, person1);
        root.insert("", person2);
        root.insert(null, person3);
        root.insert(null, family);

        family.insert(child);
        family.insert(husband);
        family.insert(wife);

        person1.insert(famC);
        person1.insert(name1);
        person2.insert(famS2);
        person2.insert(name2);
        person3.insert(famS3);
        person3.insert(name3);
    }

    /** */
    @Test
    public void testInsertFindGetObjects() {
        assertEquals(person1, root.find("I1"));
        assertEquals(person2, root.find("I2"));
        assertEquals(person3, root.find("I3"));
        assertEquals(family, root.find("F1"));
        assertNull(root.find("I4"));
        final Person person4 = new Person(root, new ObjectId("I4"));
        root.insert(null, person4);
        assertEquals(person4, root.find("I4"));
        final Person person5 = new Person(root, new ObjectId("I4"));
        root.insert("SQUIRT", person5);
        assertEquals(person5, root.find("SQUIRT"));
        final Map<String, GedObject> objects = root.getObjects();
        assertEquals(OBJECT_COUNT, objects.size());
        assertTrue(objects.keySet().contains("I1"));
        assertTrue(objects.keySet().contains("I2"));
        assertTrue(objects.keySet().contains("I3"));
        assertTrue(objects.keySet().contains("I4"));
        assertTrue(objects.keySet().contains("SQUIRT"));
        assertTrue(objects.keySet().contains("F1"));

        root.insert("nullTest", null);
        assertFalse(objects.keySet().contains("nullTest"));
    }

    /** */
    @Test
    public void testSetGetFilename() {
        assertNull(root.getFilename());
        root.setFilename("foo.ged");
        assertEquals("foo.ged", root.getFilename());
    }

    /** */
    @Test
    public void testSetGetDbName() {
        assertNull(root.getDbName());
        root.setDbName("foo");
        assertEquals("foo", root.getDbName());
    }

    /** */
    @Test
    public void testIndex() {
        final Index index = root.getIndex();
        assertEquals(0, index.surnameCount());
        root.initIndex();
        assertEquals(2, index.surnameCount());
        final Set<String> npsSchoeller = index.getNamesPerSurname("Schoeller");
        assertEquals(2, npsSchoeller.size());
        final Set<String> npsHayes = index.getNamesPerSurname("Hayes");
        assertEquals(1, npsHayes.size());
        final Set<String> nullish = index.getNamesPerSurname("Mumble");
        assertEquals(0, nullish.size());
    }

    /** */
    @Test
    public void testRootGedObject() {
        Root localRoot;
        localRoot = new Root(null);
        assertNull(localRoot.getParent());
        assertEquals("", localRoot.getString());

        localRoot = new Root(person1);
        assertEquals(person1, localRoot.getParent());
        assertEquals("", localRoot.getString());
    }

    /** */
    @Test
    public void testRootGedObjectString() {
        Root localRoot;
        localRoot = new Root(null, (String) null);
        assertNull(localRoot.getParent());
        assertEquals("", localRoot.getString());

        localRoot = new Root(person1, (String) null);
        assertEquals(person1, localRoot.getParent());
        assertEquals("", localRoot.getString());

        localRoot = new Root(null, "");
        assertNull(localRoot.getParent());
        assertEquals("", localRoot.getString());

        localRoot = new Root(person1, "");
        assertEquals(person1, localRoot.getParent());
        assertEquals("", localRoot.getString());

        localRoot = new Root(null, "Root");
        assertNull(localRoot.getParent());
        assertEquals("Root", localRoot.getString());

        localRoot = new Root(person1, "Lunk");
        assertEquals(person1, localRoot.getParent());
        assertEquals("Lunk", localRoot.getString());
    }
}
