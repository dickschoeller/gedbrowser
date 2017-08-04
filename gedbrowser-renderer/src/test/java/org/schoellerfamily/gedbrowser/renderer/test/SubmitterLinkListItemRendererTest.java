package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmitterLinkListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmitterLink submitterLink;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Root root = new Root("Root");
        /** */
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submitter submitter = new Submitter(root, new ObjectId("S1"));
        final Name name = new Name(submitter, "Richard/Schoeller/");
        root.insert(submitter);
        submitter.insert(name);

        submitterLink = new SubmitterLink(head, "SUBM", new ObjectId("S1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final SubmitterLinkRenderer slr = new SubmitterLinkRenderer(
                submitterLink, new GedRendererFactory(),
                anonymousContext);
        final SubmitterLinkListItemRenderer lir =
                (SubmitterLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Submitter:</span> <a class=\"name\""
                + " href=\"submitter?db=null&amp;id=S1\">"
                + "Richard Schoeller [S1]</a>",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemNewLine() {
        final SubmitterLinkRenderer slr = new SubmitterLinkRenderer(
                submitterLink, new GedRendererFactory(),
                anonymousContext);
        final SubmitterLinkListItemRenderer lir =
                (SubmitterLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, true, 0);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Submitter:</span> <a class=\"name\""
                + " href=\"submitter?db=null&amp;id=S1\">"
                + "Richard Schoeller [S1]</a>",
                builder.toString());
    }

    /** */
    @Test
    public void testRenderAsListItemPad() {
        final SubmitterLinkRenderer slr = new SubmitterLinkRenderer(
                submitterLink, new GedRendererFactory(),
                anonymousContext);
        final SubmitterLinkListItemRenderer lir =
                (SubmitterLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 2);
        assertEquals("Rendered html doesn't match expectation",
                "<span class=\"label\">Submitter:</span> <a class=\"name\""
                + " href=\"submitter?db=null&amp;id=S1\">"
                + "Richard Schoeller [S1]</a>",
                builder.toString());
    }
}
