package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.renderer.GedResourceNotFoundRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class GedResourceNotFoundRendererTest {
    /** */
    private Exception throwable;

    /**
     * Object under test.
     */
    private GedResourceNotFoundRenderer renderer;

    /** */
    @Before
    public void init() {
        final RenderingContext context =
                RenderingContext.user(new ApplicationInfoStub());
        throwable = new Exception("This is a test");
        renderer = new GedResourceNotFoundRenderer(throwable, context);
    }

    /** */
    @Test
    public void testGetMessage() {
        assertEquals("Mismatched message",
                "This is a test", renderer.getMessage());
    }

    /** */
    @Test
    public void testGetException() {
        assertSame("Mismatched exception", throwable, renderer.getException());
    }
}
