package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.renderer.NameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;

/**
 * @author Dick Schoeller
 */
final class NullNameHtmlRendererTest {
    /** */
    private transient NameHtmlRenderer nameHtmlRenderer;

    /** */
    @BeforeEach
    void setUp() {
        nameHtmlRenderer = new NullNameHtmlRenderer();
    }

    /** */
    @Test
    void testGetNameHtml() {
        assertEquals("", nameHtmlRenderer.getNameHtml(), "expected empty string");
    }
}
