package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlacePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public final class PlacePhraseRendererTest {
    /** */
    private transient Attribute attribute;

    /** */
    private transient Place place;

    /** */
    private CalendarProvider provider;

    /** */
    private RenderingContext anonymousContext;

    /** */
    @Before
    public void init() {
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        place = new Place(attribute, "Fayetteville, NC");
        attribute.addAttribute(place);
        provider = new CalendarProviderStub();
        final ApplicationInfo appInfo = new ApplicationInfoStub();
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetRenderAsPhrase() {
        final PlaceRenderer dRenderer = new PlaceRenderer(place,
                new GedRendererFactory(), anonymousContext, provider);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("Rendered string doesn't match expectation",
                "Fayetteville, NC", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemEmpty() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, ""), new GedRendererFactory(),
                anonymousContext, provider);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemNull() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, null), new GedRendererFactory(),
                anonymousContext, provider);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("Expected empty string", "", string);
    }
}
