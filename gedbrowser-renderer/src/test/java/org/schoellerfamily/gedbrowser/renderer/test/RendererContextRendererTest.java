package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    @Test
    void testEscapeStringLessThan() {
        final String expected = "foo&lt;foo";
        final String actual = RenderingContextRenderer.escapeString("foo<foo");
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @Test
    void testEscapeStringGreaterThan() {
        final String expected = "foo&gt;foo";
        final String actual = RenderingContextRenderer.escapeString("foo>foo");
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @Test
    void testEscapeStringMultiple() {
        final String expected = "foo&gt;bar&lt;bat&amp;xyzzy";
        final String actual =
                RenderingContextRenderer.escapeString("foo>bar<bat&xyzzy");
        assertEquals(expected, actual, "Escaping html didn't work");
    }
    /** */
    @Test
    void testEscapeStringAmpersandDelimit() {
        final String actual = RenderingContextRenderer.escapeString(" ",
                "foo&foo");
        final String expected = " foo&amp;foo";
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @Test
    void testEscapeStringLessThanDelimit() {
        final String actual = RenderingContextRenderer.escapeString("X",
                "foo<foo");
        final String expected = "Xfoo&lt;foo";
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @Test
    void testEscapeStringGreaterThanDelimit() {
        final String expected = "Yfoo&gt;foo";
        final String actual = RenderingContextRenderer.escapeString("Y",
                "foo>foo");
        assertEquals(expected, actual, "Escaping html didn't work");
    }

    /** */
    @Test
    void testEscapeStringMultipleDelimit() {
        final String expected = "plughfoo&gt;bar&lt;bat&amp;xyzzy";
        final String actual = RenderingContextRenderer.escapeString("plugh",
                "foo>bar<bat&xyzzy");
        assertEquals(expected, actual, "Escaping html didn't work");
    }
}
