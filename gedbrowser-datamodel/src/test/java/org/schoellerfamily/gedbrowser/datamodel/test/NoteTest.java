package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public class NoteTest {
    /** */
    private GedObjectBuilder builder;

    /** */
    @Before
    public void setUp() {
        builder = new GedObjectBuilder();
    }

    /** */
    @Test
    public void testNoteGedObjectCompare() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"));
        root.insert(note);
        final GedObject gob = root.find("N1");
        assertEquals("Found wrong note", note, gob);
    }

    /** */
    @Test
    public void testNoteGedObjectGetString() {
        final Note note = new Note();
        assertTrue("Note string should be empty", note.getString()
                .isEmpty());
    }

    /** */
    @Test
    public void testNoteGedObjectGetTail() {
        final Note note = new Note();
        assertTrue("Note string should be empty", note.getTail()
                .isEmpty());
    }

    /** */
    @Test
    public void testNoteGedObjectSetTailNull() {
        final Note note = new Note();
        note.setTail(null);
        assertTrue("Note string should be empty", note.getTail()
                .isEmpty());
    }

    /** */
    @Test
    public void testNoteGedObjectSetTailEmpty() {
        final Note note = new Note();
        note.setTail("");
        assertTrue("Note string should be empty", note.getTail()
                .isEmpty());
    }

    /** */
    @Test
    public void testNoteGedObjectStringGetString() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"));
        root.insert(note);
        assertEquals("Expected note 1", "N1", note.getString());
    }

    /** */
    @Test
    public void testNoteGetSubmitterText() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"), "Some note text");
        root.insert(note);
        assertEquals("Expected note 1", "Some note text", note.getTail());
    }

    /** */
    @Test
    public void testNoteGetSubmitterTextAppend() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"), "Some note te");
        root.insert(note);
        note.appendString("xt");
        assertEquals("Expected note 1", "Some note text", note.getTail());
    }
}
