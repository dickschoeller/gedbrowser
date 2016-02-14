package org.schoellerfamily.gedbrowser.renderer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkListItemRendererTest {
    /** */
    private transient SubmittorLink submittorLink;

    /** */
    @Before
    public final void init() {
        final Root root = new Root(null, "Root");
        /** */
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submittor submittor = new Submittor(root, "SUBM", "S1");
        final Name name = new Name(submittor, "Richard/Schoeller/");
        root.insert(submittor);
        submittor.insert(name);

        submittorLink = new SubmittorLink(head, "SUBL", new ObjectId("S1"));
    }

    /** */
    @Test
    public final void testRenderAsListItem() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous());
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("wrong string",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderAsListItemNewLine() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous());
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, true, 0);
        assertEquals("wrong string",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderAsListItemPad() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(),
                RenderingContext.anonymous());
        final SubmittorLinkListItemRenderer lir =
                (SubmittorLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 2);
        assertEquals("wrong string",
                "[<a href=\"source?db=null&amp;id=S1\">S1</a>]",
                builder.toString());
    }
}
