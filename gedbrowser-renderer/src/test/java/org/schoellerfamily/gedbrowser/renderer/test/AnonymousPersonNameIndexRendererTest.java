package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
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
final class AnonymousPersonNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Person person;

    /** */
    private transient RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        person = builder.createPerson("I1");
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testGetNameHtmlNull() {
        final Name name = new Name(person);
        person.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(person,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Living", pnhr.getIndexName(), "Rendered string doesn't match expectation");
    }

    /** */
    @ParameterizedTest
    @MethodSource("nameIndexCases")
    void testGetNameHtml(final String nameValue) {
        renderAndCheckNameIndex(nameValue);
    }

    private static Stream<org.junit.jupiter.params.provider.Arguments> nameIndexCases() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(""),
            org.junit.jupiter.params.provider.Arguments.of("/Schoeller/"),
            org.junit.jupiter.params.provider.Arguments.of("Richard/Schoeller/"),
            org.junit.jupiter.params.provider.Arguments.of("/Deng/Shao Ping"),
            org.junit.jupiter.params.provider.Arguments.of("Karl Frederick/Schoeller/Sr."));
    }

    private void renderAndCheckNameIndex(final String nameValue) {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person testPerson = builder.createPerson("I1");
        final Name name = nameValue.isEmpty() ? new Name(testPerson) : new Name(testPerson, nameValue);
        testPerson.addAttribute(name);
        final PersonRenderer personRenderer = new PersonRenderer(testPerson,
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("Living", pnhr.getIndexName(), "Rendered string doesn't match expectation");
    }

    /** */
    @Test
    void testGetNameHtmlPersonUnset() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final PersonRenderer personRenderer = new PersonRenderer(
                builder.createPerson(),
                new GedRendererFactory(), anonymousContext);
        final PersonNameIndexRenderer pnhr =
                (PersonNameIndexRenderer) personRenderer.getNameIndexRenderer();
        assertEquals("", pnhr.getIndexName(), "Expected empty string");
    }


}
