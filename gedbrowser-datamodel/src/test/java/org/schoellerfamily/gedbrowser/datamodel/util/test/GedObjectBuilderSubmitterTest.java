package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * Contains tests for ged object builder submitter.
 *
 * @author Richard Schoeller
 */
final class GedObjectBuilderSubmitterTest {
    @Test
    void testCreateSubmitterSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1");
        assertEquals("SUB1", submitter.getString(), "Mismatched tag");
    }

    @Test
    void testCreateSubmitterSimpleIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1");
        assertTrue(submitter.getName().getString().isEmpty(), "Should be empty string");
    }

    @Test
    void testCreateSubmitterSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertEquals("", submitter.getString(), "Expected empty string");
    }

    @Test
    void testCreateSubmitterSimpleNoIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null);
        assertTrue(submitter.getName().getString().isEmpty(), "Should be empty string");
    }

    @Test
    void testCreateSubmitterNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("SUB1", submitter.getString(), "Mismatched tag");
    }

    @Test
    void testCreateSubmitterNamedIsNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", "John/Doe/");
        assertEquals("John/Doe/", submitter.getName().getString(), "Mismatched string");
    }

    @Test
    void testCreateSubmitterNamedNullIdHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue(submitter.getString().isEmpty(), "Expected empty string");
    }

    @Test
    void testCreateSubmitterNamedNullIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter(null, "John/Doe/");
        assertTrue(submitter.getName().getString().isEmpty(), "Expected empty string");
    }

    @Test
    void testCreateSubmitterIdNullNameHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue(submitter.getString().isEmpty(), "Expected empty string");
    }

    @Test
    void testCreateSubmitterIdNullNameIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submitter submitter = builder.createSubmitter("SUB1", null);
        assertTrue(submitter.getName().getString().isEmpty(), "Expected empty string");
    }
}
