package org.schoellerfamily.gedbrowser.renderer.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.AttributeListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.AttributeRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class AttributeListItemRendererTest {
    /** */
    private transient Attribute attribute1;

    /** */
    private transient Attribute attribute2;

    /** */
    private transient Attribute attribute3;

    /** */
    @Before
    public final void init() {
        final Person person = new Person();
        attribute1 = new Attribute(person, "String", "");
        attribute2 = new Attribute(person, "String", "Strung");
        attribute3 = new Attribute(person, "Sproing", "Spring");
        final Attribute attribute4 =
                new Attribute(attribute3, "Stinky", "Stanky");
        person.insert(attribute1);
        person.insert(attribute2);
        person.insert(attribute3);
        attribute3.insert(attribute4);
    }

    /** */
    @Test
    public final void testRenderAsListItemEmpty() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute1,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 0);
        Assert.assertEquals("Rendered html doesn't match expectation",
                "<li><span class=\"label\">String:</span> </li>\n",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderAsListItemString() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute2,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer.getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, false, 2);
        Assert.assertEquals("Rendered html doesn't match expectation",
                "<li><span class=\"label\">String:</span> Strung</li>\n",
                builder.toString());
    }

    /** */
    @Test
    public final void testRenderAsListItem() {
        final AttributeRenderer aRenderer =
                new AttributeRenderer(attribute3,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final AttributeListItemRenderer apr =
                (AttributeListItemRenderer) aRenderer .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        apr.renderAsListItem(builder, true, 0);
        Assert.assertEquals("Rendered html doesn't match expectation",
                "<li><span class=\"label\">"
                + "Sproing:</span> Spring, Stanky</li>\n", builder.toString());
    }
}
