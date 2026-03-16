package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
final class SubmissionLinkPhraseRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmissionLink submissionLink;

    /** */
    private RenderingContext anonymousContext;

    @BeforeEach
    void setUp() {
        final Root root = new Root("Root");
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submission submission =
                new Submission(root, new ObjectId("SUBN1"));
        root.insert(submission);

        submissionLink =
                new SubmissionLink(head, "Submission", new ObjectId("SUBN1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    @Test
    void testRenderAsPhrase() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(), anonymousContext);
        final SubmissionLinkPhraseRenderer slpr =
                (SubmissionLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("<a class=\"name\" "
                + "href=\"submission?db=null&amp;id=SUBN1\">"
                + "SUBN1</a>", slpr.renderAsPhrase(), "Rendered html doesn't match expectation");
    }
}
