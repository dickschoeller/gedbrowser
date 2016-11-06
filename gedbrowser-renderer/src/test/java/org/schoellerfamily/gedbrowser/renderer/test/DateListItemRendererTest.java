package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.DateListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class DateListItemRendererTest {
    /** */
    private transient Attribute attribute;

    /** */
    private transient Date date;

    /** */
    @Before
    public final void init() {
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        date = new Date(attribute, "14 December 1958");
        attribute.addAttribute(date);
    }

    /** */
    @Test
    public final void testGetRenderAsListItem() {
        final DateRenderer dRenderer =
                new DateRenderer(date, new GedRendererFactory(),
                        RenderingContext.anonymous());
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("14 December 1958", string);
    }

    /** */
    @Test
    public final void testGetRenderAsListItemEmpty() {
        final DateRenderer dRenderer =
                new DateRenderer(new Date(attribute, ""),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("", string);
    }

    /** */
    @Test
    public final void testGetRenderAsListItemNull() {
        final DateRenderer dRenderer =
                new DateRenderer(new Date(attribute, null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("", string);
    }
}
