package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SimpleNameNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @ParameterizedTest
    @MethodSource("renderCases")
    void testRenderNameVariants(final Name name, final String expected) {
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
            new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir = (SimpleNameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(expected, nnir.getIndexName(), UNEXPECTED_STRING);
    }

    private static Stream<Arguments> renderCases() {
        return Stream.of(
            Arguments.of(new Name(null, "Richard/Schoeller/"),
                " <span class=\"surname\">Schoeller</span>, Richard"),
            Arguments.of(new Name(null, "Karl Frederick/Schoeller/Jr."),
                " <span class=\"surname\">Schoeller</span>, Karl Frederick, Jr."),
            Arguments.of(new Name(null, "/Schoeller/"),
                " <span class=\"surname\">Schoeller</span>"),
            Arguments.of(new Name(null, ""), " "),
            Arguments.of(new Name(null), " "),
            Arguments.of(
                new Name(), " <span class=\"surname\">?</span>"),
            Arguments.of(
                new Name(null, "Foo//Bar"), " Foo, Bar"),
            Arguments.of(new Name(null, "Foo Bar"), " Foo Bar"));
    }
}
