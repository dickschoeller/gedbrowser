package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.RootRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorRenderer;
import org.schoellerfamily.gedbrowser.renderer.SubmittorSectionRenderer;

/**
 * @author Dick Schoeller
 */
public final class SubmittorSectionRendererTest {
    /** */
    private transient Root root;

    /** */
    private transient Submittor submittor;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        root = new Root(null);
        submittor = new Submittor(root);
        final Name name = new Name(submittor, "Richard/Schoeller/");
        root.insert(submittor);
        submittor.insert(name);
        provider = new CalendarProviderStub();
    }

    /** */
    @Test
    public void testRenderAsSection() {
        final SubmittorRenderer sRenderer = new SubmittorRenderer(submittor,
                new GedRendererFactory(), RenderingContext.anonymous(),
                provider);
        final SubmittorSectionRenderer ssRenderer =
                (SubmittorSectionRenderer) sRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        ssRenderer.renderAsSection(builder, new RootRenderer(root,
                new GedRendererFactory(),
                RenderingContext.anonymous(), provider),
                false, 0, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n"
                + "<h2 class=\"name\">SubmittorRichard/Schoeller/</h2>\n"
                + "<ul>\n</ul>", builder.toString());
    }

    // TODO test with null submittor
    // TODO test with unset submittor
}
