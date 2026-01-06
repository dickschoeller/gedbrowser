package org.schoellerfamily.gedbrowser.datamodel.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    @BeforeEach
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
        assertEquals(note, gob, "Found wrong note");
    }

    /** */
    @Test
    public void testNoteGedObjectGetString() {
        final Note note = new Note();
        assertTrue(note.getString().isEmpty(), "Note string should be empty");
    }

    /** */
    @Test
    public void testNoteGedObjectGetTail() {
        final Note note = new Note();
        assertTrue(note.getTail().isEmpty(), "Note string should be empty");
    }

    /** */
    @Test
    public void testNoteGedObjectSetTailNull() {
        final Note note = new Note();
        note.setTail(null);
        assertTrue(note.getTail().isEmpty(), "Note string should be empty");
    }

    /** */
    @Test
    public void testNoteGedObjectSetTailEmpty() {
        final Note note = new Note();
        note.setTail("");
        assertTrue(note.getTail().isEmpty(), "Note string should be empty");
    }

    /** */
    @Test
    public void testNoteGedObjectStringGetString() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"));
        root.insert(note);
        assertEquals("N1", note.getString(), "Expected note 1");
    }

    /** */
    @Test
    public void testNoteGetSubmitterText() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"), "Some note text");
        root.insert(note);
        assertEquals("Some note text", note.getTail(), "Expected note 1");
    }

    /** */
    @Test
    public void testNoteGetSubmitterTextAppend() {
        final Root root = builder.getRoot();
        final Note note = new Note(root, new ObjectId("N1"), "Some note te");
        root.insert(note);
        note.appendString("xt");
        assertEquals("Some note text", note.getTail(), "Expected note 1");
    }
}
