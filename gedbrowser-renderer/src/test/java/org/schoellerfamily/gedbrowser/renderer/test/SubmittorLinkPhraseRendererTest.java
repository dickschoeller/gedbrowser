package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmittorLinkPhraseRendererTest {
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmittorLink submittorLink;

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

        final Submittor submittor = new Submittor(root, new ObjectId("S1"));
        final Name name = new Name(submittor, "Richard/Schoeller/");
        root.insert(submittor);
        submittor.insert(name);

        submittorLink = new SubmittorLink(head, "SUBM", new ObjectId("S1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final SubmittorLinkRenderer slr = new SubmittorLinkRenderer(
                submittorLink, new GedRendererFactory(), anonymousContext,
                provider);
        final SubmittorLinkPhraseRenderer slpr =
                (SubmittorLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("Rendered html doesn't match expectation",
                "<a class=\"name\" "
                + "href=\"source?db=null&amp;id=S1\">Richard/Schoeller/</a>",
                slpr.renderAsPhrase());
    }
}
