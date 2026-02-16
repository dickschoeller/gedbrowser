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
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonNameIndexRenderer;
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
final class PersonNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext userContext;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        userContext = RenderingContext.user(appInfo);
    }

    @Test
    void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        final PersonNameIndexRenderer pnhr = (PersonNameIndexRenderer) personRenderer
            .getNameIndexRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">?</span> (I1)</a>",
            pnhr.getIndexName(), "Rendered html doesn't match expectation");
    }

    @Test
    void testGetNameHtmlEmpty() {
        final Name name = new Name(person, "");
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        final PersonNameIndexRenderer pnhr = (PersonNameIndexRenderer) personRenderer
            .getNameIndexRenderer();
        assertEquals(
            "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">?</span> (I1)</a>",
            pnhr.getIndexName(), "Rendered html doesn't match expectation");
    }

    @ParameterizedTest
    @MethodSource("nameHtmlCases")
    void testGetNameHtml(final String nameValue, final String expected) {
        final Name name = new Name(person, nameValue);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person, new GedRendererFactory(),
            userContext);
        final PersonNameIndexRenderer pnhr = (PersonNameIndexRenderer) personRenderer
            .getNameIndexRenderer();
        assertEquals(expected, pnhr.getIndexName(), "Rendered html doesn't match expectation");
    }

    private static Stream<Arguments> nameHtmlCases() {
        return Stream.of(
            Arguments.of("/Schoeller/",
                "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">Schoeller</span> (I1)</a>"),
            Arguments.of("Richard/Schoeller/",
                "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">Schoeller</span>, Richard (I1)</a>"),
            Arguments.of("/Deng/Shao Ping",
                "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                    + " <span class=\"surname\">Deng</span>, Shao Ping (I1)</a>"),
            Arguments.of("Karl Frederick/Schoeller/Sr.",
                "<a href=\"person?db=null&amp;id=I1\" class=\"name\">"
                + " <span class=\"surname\">Schoeller</span>, Karl Frederick, Sr. (I1)</a>"));
    }

    @Test
    void testGetNameHtmlPersonUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final PersonRenderer personRenderer = new PersonRenderer(builder.createPerson(),
            new GedRendererFactory(), userContext);
        final PersonNameIndexRenderer pnhr = (PersonNameIndexRenderer) personRenderer
            .getNameIndexRenderer();
        assertEquals("", pnhr.getIndexName(), "Expected empty string");
    }
}
