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
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



/**
 * Contains tests for anonymous mode person name html renderer.
 *
 * @author Richard Schoeller
 */
@SuppressWarnings("PMD.CommentSize")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class AnonymousModePersonNameHtmlRendererTest {
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
        renderingContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), renderingContext);
        final PersonNameHtmlRenderer pnhr =
                (PersonNameHtmlRenderer) personRenderer.getNameHtmlRenderer();
        assertEquals("Living", pnhr.getNameHtml(), "Expected Living");
    }

    @ParameterizedTest
    @MethodSource("nameHtmlCases")
    void testGetNameHtml(final String nameValue) {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        final Person testPerson = builder.createPerson("I1");
        final Name name =
            nameValue.isEmpty() ? new Name(testPerson) : new Name(testPerson, nameValue);
        testPerson.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(testPerson,
            new GedRendererFactory(), renderingContext);
        final NameHtmlRenderer pnhr = personRenderer.getNameHtmlRenderer();
        assertEquals("Living", pnhr.getNameHtml(), "Expected Living");
    }

    private static Stream<Arguments> nameHtmlCases() {
        return Stream.of(
            Arguments.of(""),
            Arguments.of("/Schoeller/"),
            Arguments.of("Richard/Schoeller/"),
            Arguments.of("/Deng/Shao Ping"),
            Arguments.of("Karl Frederick/Schoeller/Sr."));
    }

    @Test
    void testGetNameHtmlPersonUnset() {
        final GedObjectBuilder builder = new GedObjectBuilderImpl();
        final PersonRenderer personRenderer = new PersonRenderer(
            builder.createPerson(),
            new GedRendererFactory(),
            renderingContext);
        final NameHtmlRenderer pnhr = personRenderer.getNameHtmlRenderer();
        assertEquals("", pnhr.getNameHtml(), "Expected empty name");
    }
}
