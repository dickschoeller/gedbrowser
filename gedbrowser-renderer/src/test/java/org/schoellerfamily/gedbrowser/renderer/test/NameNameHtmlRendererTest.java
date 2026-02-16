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
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ContextConfiguration(classes = { TestConfiguration.class })
@ExtendWith(SpringExtension.class)
final class NameNameHtmlRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(" <span class=\"surname\">?</span>", nameHtmlRenderer.getNameHtml(),
            "Rendered string mismatch");
    }

    @ParameterizedTest
    @MethodSource("nameHtmlCases")
    void testGetNameHtml(final String nameValue, final String expected) {
        final Name name = new Name(person, nameValue);
        person.addAttribute(name);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
                anonymousContext);
        final NameHtmlRenderer nameHtmlRenderer = new NameNameHtmlRenderer(nameRenderer);
        assertEquals(expected, nameHtmlRenderer.getNameHtml(), "Rendered string mismatch");
    }

    private static Stream<Arguments> nameHtmlCases() {
        return Stream.of(
            Arguments.of("", " <span class=\"surname\">?</span>"),
            Arguments.of("/Schoeller/", " <span class=\"surname\">Schoeller</span>"),
            Arguments.of("Richard/Schoeller/", "Richard <span class=\"surname\">Schoeller</span>"),
            Arguments.of("/Deng/Shao Ping", " <span class=\"surname\">Deng</span> Shao Ping"),
            Arguments.of("Karl Frederick/Schoeller/Sr.", "Karl Frederick <span class=\"surname\">"
                + "Schoeller</span> Sr."),
            Arguments.of("//", " <span class=\"surname\">?</span>"));
    }
}
