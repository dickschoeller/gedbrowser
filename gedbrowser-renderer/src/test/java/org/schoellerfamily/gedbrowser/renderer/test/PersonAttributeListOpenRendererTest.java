package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class PersonAttributeListOpenRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

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
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testGetAttributeListOpenNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder builder = new StringBuilder();
        pnhr.renderAttributeListOpen(builder, 0, person);
        final String string = builder.toString();
        assertEquals("\n" + HORIZONTAL + HEAD_3, string, "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    void testGetAttributeListOpenPersonUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final PersonRenderer personRenderer = new PersonRenderer(
                builder.createPerson(),
                new GedRendererFactory(), anonymousContext);
        final PersonAttributeListOpenRenderer pnhr =
                (PersonAttributeListOpenRenderer) personRenderer
                .getAttributeListOpenRenderer();
        final StringBuilder stringbuilder = new StringBuilder();
        pnhr.renderAttributeListOpen(stringbuilder, 0, person);
        final String string = stringbuilder.toString();
        assertEquals("", string, "Expected empty string");
    }
}
