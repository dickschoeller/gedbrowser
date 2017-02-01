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
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;

/**
 * @author Dick Schoeller
 */
public final class SubmittorLinkListItemRendererTest {
    /** */
    private transient SubmittorLink submittorLink;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        final Root root = new Root(null, "Root");
        /** */
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
    public void testRenderAsListItem() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemNewLine() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, true, 0);
        assertEquals("Rendered html doesn't match expectation",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemPad() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 2);
        assertEquals("Rendered html doesn't match expectation",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }
}
