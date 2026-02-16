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
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NameRenderer;
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
final class NameNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testRenderSimple() {
        renderIndexName("Richard/Schoeller/",
                " <span class=\"surname\">Schoeller</span>, Richard");
    }

    /** */
    @ParameterizedTest
    @MethodSource("renderIndexNameCases")
    void testRenderIndexName(final String nameValue, final String expected) {
        renderIndexName(nameValue, expected);
    }

    /** */
    private static Stream<Arguments> renderIndexNameCases() {
        return Stream.of(
                Arguments.of("Karl Frederick/Schoeller/Jr.",
                        " <span class=\"surname\">Schoeller</span>,"
                                + " Karl Frederick, Jr."),
                Arguments.of("/Schoeller/",
                        " <span class=\"surname\">Schoeller</span>"),
                Arguments.of("", " <span class=\"surname\">?</span>"),
                Arguments.of(null, " <span class=\"surname\">?</span>"),
                Arguments.of("Foo//Bar",
                        " <span class=\"surname\">?</span>, Foo, Bar"));
    }

    /** */
    private void renderIndexName(final String nameValue, final String expected) {
        final Name name = nameValue == null ? new Name(null)
                : new Name(null, nameValue);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(expected, nnir.getIndexName(), UNEXPECTED_STRING);
    }
}
