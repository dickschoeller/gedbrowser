package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilderImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for user mode person name html renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class UserModePersonNameHtmlRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext renderingContext;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        person = builder.createPerson("I1");
        renderingContext = RenderingContext.user(appInfo);
    }

    @ParameterizedTest(name = "should render unknown surname for name {0}")
    @MethodSource("emptyNameCases")
    void testGetNameHtmlEmpty(final String nameValue) {
        final Name name = nameValue == null ? new Name(person) : new Name(person, nameValue);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">?</span> [I1]</a>",
            pnhr.getNameHtml(), "Rendered string mismatch");
    }

    private static Stream<Arguments> emptyNameCases() {
        return Stream.of(
            Arguments.of((String) null),
            Arguments.of("")
        );
    }

    @Test
    void testGetNameHtmlSurnameOnly() {
        final Name name = new Name(person, "/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">Schoeller</span> [I1]</a>",
            pnhr.getNameHtml(), "Rendered string mismatch");
    }

    @Test
    void testGetNameHtmlSurnameLast() {
        final Name name = new Name(person, "Richard/Schoeller/");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" " + "class=\"name\">Richard"
                + " <span class=\"surname\">Schoeller</span> [I1]</a>",
            pnhr.getNameHtml(), "Rendered string mismatch");
    }

    @Test
    void testGetNameHtmlSurnameFirst() {
        final Name name = new Name(person, "/Deng/Shao Ping");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">Deng</span> Shao Ping [I1]</a>",
            pnhr.getNameHtml(), "Rendered string mismatch");
    }

    @Test
    void testGetNameHtmlSurnameMiddle() {
        final Name name = new Name(person, "Karl Frederick/Schoeller/Sr.");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" " + "class=\"name\">Karl Frederick"
                + " <span class=\"surname\">Schoeller</span> Sr. [I1]</a>",
            pnhr.getNameHtml(), "Rendered string mismatch");
    }

    @Test
    void testGetNameHtmlPersonUnset() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        final PersonRenderer personRenderer = new PersonRenderer(builder.createPerson(),
            new GedRendererFactory(), renderingContext);
        final PersonNameHtmlRenderer pnhr = (PersonNameHtmlRenderer) personRenderer
            .getNameHtmlRenderer();
        assertEquals("", pnhr.getNameHtml(), "Rendered string mismatch (expected empty)");
    }
}
