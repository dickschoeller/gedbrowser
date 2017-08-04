package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilderSubmitterTest {
    /** */
    @Test
    public void testCreateSubmitterSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1");
        assertEquals("Mismatched tag", "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1");
        assertTrue("Should be empty string",
                submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertEquals("Expected empty string", "", submitter.getString());
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleNoIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue("Should be empty string",
                submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter =
                builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("Mismatched tag", "SUB1", submitter.getString());
    }

    /** */
    @Test
    public void testCreateSubmitterNamedIsNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter =
                builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("Mismatched string",
                "John/Doe/", submitter.getName().getString());
    }

    /** */
    @Test
    public void testCreateSubmitterNamedNullIdHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue("Expected empty string", submitter.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterNamedNullIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue("Expected empty string",
                submitter.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterIdNullNameHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue("Expected empty string", submitter.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmitterIdNullNameIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue("Expected empty string",
                submitter.getName().getString().isEmpty());
    }
}
