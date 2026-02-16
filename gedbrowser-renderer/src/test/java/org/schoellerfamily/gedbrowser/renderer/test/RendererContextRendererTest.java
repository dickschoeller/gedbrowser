package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.schoellerfamily.gedbrowser.renderer.RenderingContextRenderer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
class RendererContextRendererTest {
    /** */
    @Test
    void testEscapeStringAmpersand() {
        final String expected = "foo&amp;foo";
        final String actual = RenderingContextRenderer.escapeString("foo&foo");
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @ParameterizedTest
    @MethodSource("escapeStringCases")
    void testEscapeString(final String input, final String expected) {
        assertEquals(expected, RenderingContextRenderer.escapeString(input),
                "Escaping html didn't work");
    }

    /** */
    @ParameterizedTest
    @MethodSource("escapeStringDelimitedCases")
    void testEscapeStringDelimited(final String delimiter, final String input,
            final String expected) {
        assertEquals(expected,
                RenderingContextRenderer.escapeString(delimiter, input),
                "Escaping html didn't work");
    }

    /** */
    private static Stream<Arguments> escapeStringCases() {
        return Stream.of(
            Arguments.of("foo<foo", "foo&lt;foo"),
            Arguments.of("foo>foo", "foo&gt;foo"),
            Arguments.of("foo>bar<bat&xyzzy", "foo&gt;bar&lt;bat&amp;xyzzy"));
    }

    /** */
    private static Stream<Arguments> escapeStringDelimitedCases() {
        return Stream.of(
            Arguments.of(" ", "foo&foo", " foo&amp;foo"),
            Arguments.of("X", "foo<foo", "Xfoo&lt;foo"),
            Arguments.of("Y", "foo>foo", "Yfoo&gt;foo"),
            Arguments.of("plugh", "foo>bar<bat&xyzzy", "plughfoo&gt;bar&lt;bat&amp;xyzzy"));
    }
}
