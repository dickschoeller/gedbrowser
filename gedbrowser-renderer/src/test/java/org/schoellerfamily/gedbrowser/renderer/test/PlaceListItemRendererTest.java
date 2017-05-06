package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlaceListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class PlaceListItemRendererTest {
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    private transient Attribute attribute;

    /** */
    private transient Place place;

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
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetRenderAsListItem() {
        final PlaceRenderer dRenderer = new PlaceRenderer(place,
                new GedRendererFactory(), anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Rendered string doesn't match expectation",
                "Fayetteville, NC", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemEmpty() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, ""), new GedRendererFactory(),
                anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public void testGetRenderAsListItemNull() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, null), new GedRendererFactory(),
                anonymousContext);
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        assertEquals("Expected empty string", "", string);
    }
}
