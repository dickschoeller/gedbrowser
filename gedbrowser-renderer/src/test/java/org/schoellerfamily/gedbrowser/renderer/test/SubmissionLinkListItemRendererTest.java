package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmissionLinkListItemRenderer;
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
@SuppressWarnings("null")
public final class SubmissionLinkListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient SubmissionLink submissionLink;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @BeforeEach
    public void init() {
        final Root root = new Root("Root");
        /** */
        final Head head = new Head(root, "Head");
        root.insert(head);

        final Submission submission = new Submission(root, new ObjectId("S1"));
        final Name name = new Name(submission, "Richard/Schoeller/");
        root.insert(submission);
        submission.insert(name);

        submissionLink = new SubmissionLink(head, "SUBM", new ObjectId("S1"));
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testRenderAsListItem() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(),
                anonymousContext);
        final SubmissionLinkListItemRenderer lir =
                (SubmissionLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 0);
        assertEquals("<span class=\"label\">Submission:</span> <a class=\"name\""
                + " href=\"submission?db=null&amp;id=S1\">S1</a>", builder.toString(), "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    public void testRenderAsListItemNewLine() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(),
                anonymousContext);
        final SubmissionLinkListItemRenderer lir =
                (SubmissionLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, true, 0);
        assertEquals("<span class=\"label\">Submission:</span> <a class=\"name\""
                + " href=\"submission?db=null&amp;id=S1\">S1</a>", builder.toString(), "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    public void testRenderAsListItemPad() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(),
                anonymousContext);
        final SubmissionLinkListItemRenderer lir =
                (SubmissionLinkListItemRenderer) slr.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        lir.renderAsListItem(builder, false, 2);
        assertEquals("<span class=\"label\">Submission:</span> <a class=\"name\""
                + " href=\"submission?db=null&amp;id=S1\">S1</a>", builder.toString(), "Rendered html doesn't match expectation");
    }

    /** */
    @Test
    public void testGetListItemContents() {
        final SubmissionLinkRenderer slr = new SubmissionLinkRenderer(
                submissionLink, new GedRendererFactory(),
                anonymousContext);
        final SubmissionLinkListItemRenderer lir =
                (SubmissionLinkListItemRenderer) slr.getListItemRenderer();
        assertEquals("<span class=\"label\">Submission:</span> <a class=\"name\""
                + " href=\"submission?db=null&amp;id=S1\">S1</a>", lir.getListItemContents(), "contents don't match");
    }
}