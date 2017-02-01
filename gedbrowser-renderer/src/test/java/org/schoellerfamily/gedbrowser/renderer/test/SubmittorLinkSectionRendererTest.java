package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.RootRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkSectionRenderer;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLinkSectionRendererTest {
    /** */
    private transient Root root;

    /** */
    private transient SubmittorLink submittorLink;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        root = new Root(null, "Root");
        final Head head = new Head(root, "Head");
        root.insert(head);
        final Submittor submittor = new Submittor(root, "SUBM", "S1");
        final Name name = new Name(submittor, "Richard/Schoeller/");
        root.insert(submittor);
        submittor.insert(name);
        submittorLink = new SubmittorLink(head, "SUBL", new ObjectId("S1"));
        provider = new CalendarProviderStub();
    }

    /** */
    @Test
    public void testRenderAsSection() {
        final SubmittorLinkRenderer slRenderer = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous(), provider),
                false, 0, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsSectionNewLine() {
        final SubmittorLinkRenderer slRenderer = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous(), provider),
                true, 0, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }
    /** */
    @Test
    public void testRenderAsSectionPad() {
        final SubmittorLinkRenderer slRenderer = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous(), provider),
                false, 2, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }
}
