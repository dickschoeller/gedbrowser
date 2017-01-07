package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkPhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorLinkRenderer;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkPhraseRendererTest {
    /** */
    private transient SubmittorLink submittorLink;

    /** */
    @Before
    public final void init() {
        /** */
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
    public final void testRenderAsPhrase() {
        final SubmittorLinkRenderer slr =
                new SubmittorLinkRenderer(submittorLink,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SubmittorLinkPhraseRenderer slpr =
                (SubmittorLinkPhraseRenderer) slr.getPhraseRenderer();
        assertEquals("Rendered html doesn't match expectation",
                "<a class=\"name\" "
                + "href=\"source?db=null&amp;id=S1\">S1</a>",
                slpr.renderAsPhrase());
    }
}
