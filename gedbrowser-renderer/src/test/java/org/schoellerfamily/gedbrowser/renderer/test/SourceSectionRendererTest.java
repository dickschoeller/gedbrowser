package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.SourceRenderer;
import org.schoellerfamily.gedbrowser.renderer.SourceSectionRenderer;

/**
 * @author Dick Schoeller
 */
public final class SourceSectionRendererTest {
    /** */
    private transient Root root;

    /** */
    private transient RenderingContext renderingContext;

    /** */
    @Before
    public void init() {
        root = new Root(null, "root");
        renderingContext = RenderingContext.anonymous();
    }

    /** */
    @Test
    public void testRenderAsSection() {
        final Source source = new Source(root, new ObjectId("S1"));
        root.insert(source);
        final Attribute attribute1 = new Attribute(source, "ATTR", "Attr 1");
        final Attribute attribute2 = new Attribute(source, "ATTR", "Attr 2");
        source.addAttribute(attribute1);
        source.addAttribute(attribute2);
        final SourceRenderer sRenderer =
                new SourceRenderer(source, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final SourceSectionRenderer ssRenderer =
                (SourceSectionRenderer) sRenderer.getSectionRenderer();
        final StringBuilder builder = new StringBuilder();
        ssRenderer.renderAsSection(builder, new PersonRenderer(new Person(root),
                new GedRendererFactory(), renderingContext), false, 0, 1);
        assertEquals("Rendered html doesn't match expectation",
                "\n" + "<ul>\n"
                + "<li><span class=\"label\">ATTR:</span> Attr 1</li>\n"
                + "<li><span class=\"label\">ATTR:</span> Attr 2</li>\n"
                + "</ul>", builder.toString());
    }

    // TODO vary the inputs
}
