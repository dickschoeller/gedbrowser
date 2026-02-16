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
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameListItemRenderer;
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
final class SimpleNameListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";
    /** */
    private static final String EXPECT_EMPTY = "Expected empty string";

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
        renderAsListItem("Richard /Schoeller/", false, 0,
                "Richard Schoeller", UNEXPECTED_STRING);
    }

    /** */
    @ParameterizedTest
    @MethodSource("renderAsListItemCases")
    void testRenderAsListItem(final String nameValue, final boolean newLine,
            final int pad, final String expected, final String message) {
        renderAsListItem(nameValue, newLine, pad, expected, message);
    }

    /** */
    private static Stream<Arguments> renderAsListItemCases() {
        return Stream.of(
                Arguments.of("Karl Frederick /Schoeller/Jr.", false, 0,
                        "Karl Frederick Schoeller Jr.", UNEXPECTED_STRING),
                Arguments.of("Karl Frederick /Schoeller/Jr.", false, 1, "",
                        EXPECT_EMPTY),
                Arguments.of("Karl Frederick /Schoeller/Jr.", true, 0,
                        "\nKarl Frederick Schoeller Jr.", UNEXPECTED_STRING),
                Arguments.of("/Schoeller/", false, 0, "Schoeller",
                        UNEXPECTED_STRING),
                Arguments.of("", false, 0, "", UNEXPECTED_STRING),
                Arguments.of(null, false, 0, "", UNEXPECTED_STRING),
                Arguments.of("Foo//Bar", false, 0, "Foo Bar",
                        UNEXPECTED_STRING));
    }

    /** */
    private void renderAsListItem(final String nameValue,
            final boolean newLine, final int pad, final String expected,
            final String message) {
        final Name name = nameValue == null ? new Name(null)
                : new Name(null, nameValue);
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        nlir.renderAsListItem(builder, newLine, pad);
        assertEquals(expected, builder.toString(), message);
    }

    /** */
    @ParameterizedTest
    @MethodSource("listItemContentsCases")
    void testListItemContents(final String nameValue, final String expected,
            final String message) {
        assertEquals(expected, getListItemContents(nameValue), message);
    }

    /** */
    private static Stream<Arguments> listItemContentsCases() {
        return Stream.of(
                Arguments.of("Richard /Schoeller/",
                        "<span class=\"label\">Name:</span>"
                                + " Richard Schoeller",
                        UNEXPECTED_STRING),
                Arguments.of("Karl Frederick /Schoeller/Jr.",
                        "<span class=\"label\">Name:</span>"
                                + " Karl Frederick Schoeller Jr.",
                        UNEXPECTED_STRING),
                Arguments.of("Karl Frederick /Schoeller/Jr.",
                        "<span class=\"label\">Name:</span>"
                                + " Karl Frederick Schoeller Jr.",
                        EXPECT_EMPTY),
                Arguments.of("Karl Frederick /Schoeller/Jr.",
                        "<span class=\"label\">Name:</span>"
                                + " Karl Frederick Schoeller Jr.",
                        UNEXPECTED_STRING),
                Arguments.of("/Schoeller/",
                        "<span class=\"label\">Name:</span> Schoeller",
                        UNEXPECTED_STRING),
                Arguments.of("",
                        "<span class=\"label\">Name:</span> ",
                        UNEXPECTED_STRING),
                Arguments.of(null,
                        "<span class=\"label\">Name:</span> ",
                        UNEXPECTED_STRING),
                Arguments.of("Foo//Bar",
                        "<span class=\"label\">Name:</span> Foo Bar",
                        UNEXPECTED_STRING));
    }

    /** */
    private String getListItemContents(final String nameValue) {
        final Name name = nameValue == null ? new Name(null)
                : new Name(null, nameValue);
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameListItemRenderer nlir =
                (SimpleNameListItemRenderer) nameRenderer
                    .getListItemRenderer();
        return nlir.getListItemContents();
    }
}
