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
import org.schoellerfamily.gedbrowser.renderer.SubmitterLinkPhraseRenderer;
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
public final class SubmitterLinkPhraseRendererTest {
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
        /** */
        final Root root = new Root("Root");
        /** */
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submitter submitter = new Submitter(root, new ObjectId("SUBM1"));
        final Name name = new Name(submitter, "Richard/Schoeller/");
        root.insert(submitter);
        submitter.insert(name);

        submitterLink = new SubmitterLink(head, "SUBM", new ObjectId("SUBM1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final SubmitterLinkRenderer slr = new SubmitterLinkRenderer(
                submitterLink, new GedRendererFactory(), anonymousContext);
        final SubmitterLinkPhraseRenderer slpr =
                (SubmitterLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("Rendered html doesn't match expectation",
                "<a class=\"name\" "
                + "href=\"submitter?db=null&amp;id=SUBM1\">"
                + "Richard Schoeller [SUBM1]</a>",
                slpr.renderAsPhrase());
    }
}
