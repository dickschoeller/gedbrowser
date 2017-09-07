package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkRenderer;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class SubmissionLinkPhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmissionLink submissionLink;

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

        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);

        submissionLink =
                new SubmissionLink(head, "Submission", new ObjectId("SUBN1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsPhrase() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(), anonymousContext);
        final SubmissionLinkPhraseRenderer slpr =
                (SubmissionLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("Rendered html doesn't match expectation",
                "<a class=\"name\" "
                + "href=\"submission?db=null&amp;id=SUBN1\">"
                + "SUBN1</a>",
                slpr.renderAsPhrase());
    }
}
