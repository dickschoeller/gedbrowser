package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.renderer.GedResourceNotFoundRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class GedResourceNotFoundRendererTest {
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
    @Before
    public void init() {
        final RenderingContext context = RenderingContext.user(appInfo);
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
