package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
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
        assertEquals("SUB1", submitter.getString(), "Mismatched tag");
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1");
        assertTrue(submitter.getName().getString().isEmpty(), "Should be empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterSimpleNoIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue(submitter.getName().getString().isEmpty(), "Should be empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("SUB1", submitter.getString(), "Mismatched tag");
    }

    /** */
    @Test
    public void testCreateSubmitterNamedIsNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("John/Doe/", submitter.getName().getString(), "Mismatched string");
    }

    /** */
    @Test
    public void testCreateSubmitterNamedNullIdHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue(submitter.getString().isEmpty(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterNamedNullIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue(submitter.getName().getString().isEmpty(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterIdNullNameHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue(submitter.getString().isEmpty(), "Expected empty string");
    }

    /** */
    @Test
    public void testCreateSubmitterIdNullNameIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue(submitter.getName().getString().isEmpty(), "Expected empty string");
    }
}
