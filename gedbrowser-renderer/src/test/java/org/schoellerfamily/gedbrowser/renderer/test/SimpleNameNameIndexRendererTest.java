package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.SimpleNameRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SimpleNameNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING =
            "Unexpected string returned";

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderSimple() {
        final Name name = new Name(null, "Richard/Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>, Richard",
                nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick/Schoeller/Jr.");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>,"
                + " Karl Frederick, Jr.",
                nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">Schoeller</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderEmpty() {
        final Name name = new Name(null, "");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING, " ", nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderNull() {
        final Name name = new Name(null);
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING, " ", nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderUnset() {
        final Name name = new Name();
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING,
                " <span class=\"surname\">?</span>",
                nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING, " Foo, Bar", nnir.getIndexName());
    }

    /** */
    @Test
    public void testRenderSimpleString() {
        final Name name = new Name(null, "Foo Bar");
        final SimpleNameRenderer nameRenderer = new SimpleNameRenderer(name,
                new GedRendererFactory(), anonymousContext);
        final SimpleNameNameIndexRenderer nnir =
                (SimpleNameNameIndexRenderer) nameRenderer
                    .getNameIndexRenderer();
        assertEquals(UNEXPECTED_STRING, " Foo Bar", nnir.getIndexName());
    }
}
