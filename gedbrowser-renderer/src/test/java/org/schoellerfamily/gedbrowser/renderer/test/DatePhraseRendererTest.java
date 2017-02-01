package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.renderer.DatePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.DateRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class DatePhraseRendererTest {
    /** */
    private transient Attribute attribute;

    /** */
    private transient Date date;

    /** */
    private CalendarProvider provider;

    /** */
    @Before
    public void init() {
        provider = new CalendarProviderStub();
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        date = new Date(attribute, "14 December 1958");
        attribute.addAttribute(date);
    }

    /** */
    @Test
    public void testGetRenderAsPhrase() {
        final DateRenderer dRenderer =
                new DateRenderer(date, new GedRendererFactory(),
                        RenderingContext.anonymous(),
                        provider);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer
                .getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("Rendered date string doesn't match expectation",
                "14 December 1958", string);
    }

    /** */
    @Test
    public void testGetRenderAsPhraseEmpty() {
        final DateRenderer dRenderer =
                new DateRenderer(new Date(attribute, ""),
                        new GedRendererFactory(),
                        RenderingContext.anonymous(),
                        provider);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer
                .getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testGetRenderAsPhraseNull() {
        final DateRenderer dRenderer =
                new DateRenderer(new Date(attribute, null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous(),
                        provider);
        final DatePhraseRenderer dpRenderer = (DatePhraseRenderer) dRenderer
                .getPhraseRenderer();
        final String string = dpRenderer.renderAsPhrase();
        assertEquals("Expected empty string", "", string);
    }
}
