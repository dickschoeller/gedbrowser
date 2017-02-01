package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
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
public final class DateListItemRendererTest {
    /** */
    private transient Attribute attribute;

    /** */
    private transient Date date;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        date = new Date(attribute, "14 December 1958");
        attribute.addAttribute(date);
        provider = new CalendarProviderStub();
    }

    /** */
    @Test
    public void testGetRenderAsListItem() {
        final DateRenderer dRenderer = new DateRenderer(date,
                new GedRendererFactory(), RenderingContext.anonymous(),
                provider);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Rendered string doesn't match expectation",
                "14 December 1958", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemEmpty() {
        final DateRenderer dRenderer = new DateRenderer(new Date(attribute, ""),
                new GedRendererFactory(), RenderingContext.anonymous(),
                provider);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemNull() {
        final DateRenderer dRenderer = new DateRenderer(
                new Date(attribute, null), new GedRendererFactory(),
                RenderingContext.anonymous(), provider);
        final DateListItemRenderer dlir = (DateListItemRenderer) dRenderer
                .getListItemRenderer();
        final StringBuilder builder = new StringBuilder();
        dlir.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }
}
