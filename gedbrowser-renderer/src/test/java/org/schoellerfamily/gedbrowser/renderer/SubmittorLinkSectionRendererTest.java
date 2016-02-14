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
public class SubmittorLinkSectionRendererTest {
    /** */
    private transient Root root;

    /** */
    private transient SubmittorLink submittorLink;

    /** */
    @Before
    public final void init() {
        /** */
        root = new Root(null, "Root");
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
    public final void testRenderAsSection() {
        final SubmittorLinkRenderer slRenderer =
                new SubmittorLinkRenderer(
                        submittorLink, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous()), false, 0, 0);
        assertEquals("<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderAsSectionNewLine() {
        final SubmittorLinkRenderer slRenderer =
                new SubmittorLinkRenderer(
                        submittorLink, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous()), true, 0, 0);
        assertEquals("<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }
    /** */
    @Test
    public final void testRenderAsSectionPad() {
        final SubmittorLinkRenderer slRenderer =
                new SubmittorLinkRenderer(
                        submittorLink, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SubmittorLinkSectionRenderer slsRenderer =
                (SubmittorLinkSectionRenderer) slRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        slsRenderer.renderAsSection(builder,
                new RootRenderer(root, new GedRendererFactory(),
                        RenderingContext.anonymous()), false, 2, 0);
        assertEquals("<p>\n" + "Submitted by: <a class=\"name\""
                + " href=\"source?db=null&amp;id=S1\">S1</a></p>\n",
                builder.toString());
    }
}
