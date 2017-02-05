package org.schoellerfamily.gedbrowser.datamodel.util.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;

/**
 * @author Dick Schoeller
 */
public final class GedObjectBuilderSubmittorTest {
    /** */
    @Test
    public void testCreateSubmittorSimple() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUB1");
        assertEquals("Mismatched tag", "SUB1", submittor.getString());
    }

    /** */
    @Test
    public void testCreateSubmittorSimpleIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUB1");
        assertTrue("Should be empty string",
                submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorSimpleNoId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null);
        assertEquals("Expected empty string", "", submittor.getString());
    }

    /** */
    @Test
    public void testCreateSubmittorSimpleNoIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null);
        assertTrue("Should be empty string",
                submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor =
                builder.createSubmittor("SUB1", "John/Doe/");
        assertEquals("Mismatched tag", "SUB1", submittor.getString());
    }

    /** */
    @Test
    public void testCreateSubmittorNamedIsNamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor =
                builder.createSubmittor("SUB1", "John/Doe/");
        assertEquals("Mismatched string",
                "John/Doe/", submittor.getName().getString());
    }

    /** */
    @Test
    public void testCreateSubmittorNamedNullIdHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null, "John/Doe/");
        assertTrue("Expected empty string", submittor.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorNamedNullIdIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor(null, "John/Doe/");
        assertTrue("Expected empty string",
                submittor.getName().getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorIdNullNameHasEmptyId() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUB1", null);
        assertTrue("Expected empty string", submittor.getString().isEmpty());
    }

    /** */
    @Test
    public void testCreateSubmittorIdNullNameIsUnnamed() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Submittor submittor = builder.createSubmittor("SUB1", null);
        assertTrue("Expected empty string",
                submittor.getName().getString().isEmpty());
    }
}
