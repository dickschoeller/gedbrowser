package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.NullListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameHtmlRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullNameIndexRenderer;
import org.schoellerfamily.gedbrowser.renderer.NullPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SimpleAttributeListOpenRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceSectionRenderer;

/**
 * @author Dick Schoeller
 */
public class SourceRendererTest {
    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testAttributeListOpenRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testListItemRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameHtmlRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testNameIndexRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testPhraseRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public final void testSectionRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertTrue(renderer.getSectionRenderer()
                instanceof SourceSectionRenderer);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public final void testTitleString() throws IOException {
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertEquals("Schoeller, Richard John, birth certificate",
                renderer.getTitleString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public final void testIdString() throws IOException {
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        assertEquals("S3",
                renderer.getIdString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public final void testIdAttributes() throws IOException {
        final String[] expects = {
                "<span class=\"label\">Abbreviation:</span>"
                + " SchoellerRichardBirthCert",
                "<span class=\"label\">Note:</span>"
                + " I have a certified copy of this document"
        };
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                RenderingContext.anonymous());
        int i = 0;
        for (final GedRenderer<?> attribute : renderer.getAttributes()) {
            assertEquals(expects[i++], attribute.getListItemContents());
        }
    }
}
