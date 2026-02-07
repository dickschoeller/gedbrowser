package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.renderer.GedResourceNotFoundRenderer;
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
class GedResourceNotFoundRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private Exception throwable;

    /**
     * Object under test.
     */
    private GedResourceNotFoundRenderer renderer;

    /** */
    @BeforeEach
    void setUp() {
        final RenderingContext context = RenderingContext.user(appInfo);
        throwable = new Exception("This is a test");
        renderer = new GedResourceNotFoundRenderer(throwable, context);
    }

    /** */
    @Test
    void testGetMessage() {
        assertEquals("This is a test", renderer.getMessage(), "Mismatched message");
    }

    /** */
    @Test
    void testGetException() {
        assertSame(throwable, renderer.getException(), "Mismatched exception");
    }
}
