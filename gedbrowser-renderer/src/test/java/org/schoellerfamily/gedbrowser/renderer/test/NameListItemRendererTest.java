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
import org.schoellerfamily.gedbrowser.renderer.NameListItemRenderer;
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
final class NameListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @ParameterizedTest
    @MethodSource("renderListItemCases")
    void testRenderListItem(final String nameValue, final boolean newLine, final int pad,
        final String expected) {
        final Name name = nameValue == null ? new Name(null) : new Name(null, nameValue);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameListItemRenderer nlir = (NameListItemRenderer) nameRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, newLine, pad);
        assertEquals(expected, builder.toString(),
            pad > 0 ? EXPECT_EMPTY : UNEXPECTED_STRING);
    }

    private static Stream<Arguments> renderListItemCases() {
        return Stream.of(
            Arguments.of("Richard /Schoeller/", false, 0, "Richard Schoeller"),
            Arguments.of("Karl Frederick /Schoeller/Jr.", false, 0,
                "Karl Frederick Schoeller Jr."),
            Arguments.of("Karl Frederick /Schoeller/Jr.", false, 1, ""),
            Arguments.of("Karl Frederick /Schoeller/Jr.", true, 0,
                "\nKarl Frederick Schoeller Jr."),
            Arguments.of("/Schoeller/", false, 0, "Schoeller"),
            Arguments.of("", false, 0, "?"),
            Arguments.of(null, false, 0, "?"),
            Arguments.of("Foo//Bar", false, 0, "Foo ? Bar"));
    }
}
