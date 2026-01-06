package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NameNameIndexRenderer;
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
public final class NameNameIndexRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private static final String UNEXPECTED_STRING = "Unexpected string returned";

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    void testRenderSimple() {
        final Name name = new Name(null, "Richard/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">Schoeller</span>, Richard", nnir.getIndexName(),
            UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderHarder() {
        final Name name = new Name(null, "Karl Frederick/Schoeller/Jr.");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">Schoeller</span>," + " Karl Frederick, Jr.",
            nnir.getIndexName(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderNoPrefix() {
        final Name name = new Name(null, "/Schoeller/");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">Schoeller</span>", nnir.getIndexName(),
            UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderEmpty() {
        final Name name = new Name(null, "");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">?</span>", nnir.getIndexName(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderNull() {
        final Name name = new Name(null);
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">?</span>", nnir.getIndexName(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderUnset() {
        final Name name = new Name();
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">?</span>", nnir.getIndexName(), UNEXPECTED_STRING);
    }

    /** */
    @Test
    void testRenderPrefixSuffix() {
        final Name name = new Name(null, "Foo//Bar");
        final NameRenderer nameRenderer = new NameRenderer(name, new GedRendererFactory(),
            anonymousContext);
        final NameNameIndexRenderer nnir = (NameNameIndexRenderer) nameRenderer
            .getNameIndexRenderer();
        assertEquals(" <span class=\"surname\">?</span>, Foo, Bar", nnir.getIndexName(),
            UNEXPECTED_STRING);
    }
}
