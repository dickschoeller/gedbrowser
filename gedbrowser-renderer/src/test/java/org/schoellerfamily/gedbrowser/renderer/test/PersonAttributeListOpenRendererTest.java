package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class PersonAttributeListOpenRendererTest {
    /**
     * Boiler plate for a horizontal line.
     */
    private static final String HORIZONTAL = "<hr class=\"attributes\"/>\n";

    /**
     * Boiler plate for a heading.
     */
    private static final String HEAD_3 =
            "<h3 class=\"attributes\">Attributes</h3>\n" + "<ul>\n";

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext renderingContext;

    /** */
    @Before
    public final void init() {
        Root root;
        root = new Root(null, "root");
        person = new Person(root, new ObjectId("I1"));
        root.insert(person);
        renderingContext = RenderingContext.anonymous();
    }

    /** */
    @Test
    public final void testGetAttributeListOpenNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Rendered html doesn't match expectation",
                "\n" + HORIZONTAL + HEAD_3,
                string);
    }

    /** */
    @Test
    public final void testGetAttributeListOpenPersonUnset() {
        final PersonRenderer personRenderer = new PersonRenderer(new Person(),
                new GedRendererFactory(), renderingContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }
}
