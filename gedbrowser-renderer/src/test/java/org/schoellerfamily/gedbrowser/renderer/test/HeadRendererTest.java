package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.HeadRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class HeadRendererTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final HeadRenderer renderer = new HeadRenderer(new Head(null, "Header"),
            new GedRendererFactory(), anonymousContext);
        assertTrue(
            renderer.getAttributeListOpenRenderer() instanceof SimpleAttributeListOpenRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final HeadRenderer renderer = new HeadRenderer(new Head(null, "Header"),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getListItemRenderer() instanceof NullListItemRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final HeadRenderer renderer = new HeadRenderer(new Head(null, "Header"),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameHtmlRenderer() instanceof NullNameHtmlRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testNameIndeRenderer() {
        final HeadRenderer renderer = new HeadRenderer(new Head(null, "Header"),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getNameIndexRenderer() instanceof NullNameIndexRenderer,
            "Wrong renderer type");
    }

    /**
     * Test that we are using the appropriate sub-renderers. We will test the
     * sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final HeadRenderer renderer = new HeadRenderer(new Head(null, "Header"),
            new GedRendererFactory(), anonymousContext);
        assertTrue(renderer.getPhraseRenderer() instanceof NullPhraseRenderer,
            "Wrong renderer type");
    }

    /**
     * Simple test from a data file.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeadRenderer() throws IOException {
        final Root root = reader.readBigTestSource();
        final Head head = root.find("Header", Head.class);
        final HeadRenderer renderer = new HeadRenderer(head, new GedRendererFactory(),
            anonymousContext);
        final List<GedRenderer<?>> attrRenderers = renderer.getAttributes();
        final int expected = 6;
        assertEquals(expected, attrRenderers.size(), "should not be empty");
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testHeaderMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        for (final Head head : root.find(Head.class)) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("head?db=gl120368", renderer.getHeaderHref(), "head href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Head> heads = root.find(Head.class);
        for (final Head head : heads) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("save?db=gl120368", renderer.getSaveHref(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSaveFilename() throws IOException {
        final Root root = reader.readFileTestSource();
        final Collection<Head> heads = root.find(Head.class);
        for (final Head head : heads) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("gl120368.ged", renderer.getSaveFilename(), "save href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testIndexMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        for (final Head head : root.find(Head.class)) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("surnames?db=gl120368&letter=A", renderer.getIndexHref(),
                "index href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testLivingMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        for (final Head head : root.find(Head.class)) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("living?db=gl120368", renderer.getLivingHref(), "living href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSourcesMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        for (final Head head : root.find(Head.class)) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("sources?db=gl120368", renderer.getSourcesHref(), "sources href mismatch");
        }
    }

    /**
     * Test whether the menu items are as expected.
     *
     * @throws IOException if can't read data file
     */
    @Test
    public void testSubmittersMenuItem() throws IOException {
        final Root root = reader.readFileTestSource();
        for (final Head head : root.find(Head.class)) {
            final HeadRenderer renderer = createRenderer(head);
            assertEquals("submitters?db=gl120368", renderer.getSubmittersHref(),
                "submitters href mismatch");
        }
    }

    /**
     * @param head the head to render
     * @return the renderer
     */
    private HeadRenderer createRenderer(final Head head) {
        return new HeadRenderer(head, new GedRendererFactory(), anonymousContext);
    }
}
