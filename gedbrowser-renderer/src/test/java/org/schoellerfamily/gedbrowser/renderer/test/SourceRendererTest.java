package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
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
public final class SourceRendererTest {
    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testAttributeListOpenRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getAttributeListOpenRenderer()
                instanceof SimpleAttributeListOpenRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testListItemRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getListItemRenderer()
                instanceof NullListItemRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameHtmlRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getNameHtmlRenderer()
                instanceof NullNameHtmlRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testNameIndexRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getNameIndexRenderer()
                instanceof NullNameIndexRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testPhraseRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getPhraseRenderer()
                instanceof NullPhraseRenderer);
    }

    /**
     * Test that we are using the appropriate sub-renderers.
     * We will test the sub-renderers directly.
     */
    @Test
    public void testSectionRenderer() {
        final SourceRenderer renderer = new SourceRenderer(new Source(null),
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertTrue("Wrong renderer type",
                renderer.getSectionRenderer()
                instanceof SourceSectionRenderer);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testTitleString() throws IOException {
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Mismatched title string",
                "Schoeller, Richard John, birth certificate",
                renderer.getTitleString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIdString() throws IOException {
        final Root root = (Root) TestDataReader.getInstance()
                .readBigTestSource();
        final Source source = (Source) root.find("S3");
        final SourceRenderer renderer = new SourceRenderer(source,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        assertEquals("Mismatched source ID",
                "S3",
                renderer.getIdString());
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIdAttributes() throws IOException {
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
                RenderingContext.anonymous(), provider);
        int i = 0;
        for (final GedRenderer<?> attribute : renderer.getAttributes()) {
            assertEquals("Rendered html doesn't match expectation",
                    expects[i++], attribute.getListItemContents());
        }
    }
}
