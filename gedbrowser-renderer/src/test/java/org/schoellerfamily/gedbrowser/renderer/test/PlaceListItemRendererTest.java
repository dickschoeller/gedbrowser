package org.schoellerfamily.gedbrowser.renderer.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlaceListItemRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;

/**
 * @author Dick Schoeller
 */
public class PlaceListItemRendererTest {
    /** */
    private transient Attribute attribute;

    /** */
    private transient Place place;

    /** */
    @Before
    public final void init() {
        final Person person = new Person();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        place = new Place(attribute, "Fayetteville, NC");
        attribute.addAttribute(place);
    }

    /** */
    @Test
    public final void testGetRenderAsListItem() {
        final PlaceRenderer dRenderer =
                new PlaceRenderer(place,
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        Assert.assertEquals("Rendered string doesn't match expectation",
                "Fayetteville, NC", string);
    }

    /** */
    @Test
    public final void testGetRenderAsListItemEmpty() {
        final PlaceRenderer dRenderer =
                new PlaceRenderer(new Place(attribute, ""),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        Assert.assertEquals("Expected empty string", "", string);
    }

    /** */
    @Test
    public final void testGetRenderAsListItemNull() {
        final PlaceRenderer dRenderer =
                new PlaceRenderer(new Place(attribute, null),
                        new GedRendererFactory(),
                        RenderingContext.anonymous());
        final StringBuilder builder = new StringBuilder();
        final PlaceListItemRenderer liRenderer =
                (PlaceListItemRenderer) dRenderer.getListItemRenderer();
        liRenderer.renderAsListItem(builder, false, 0);
        final String string = builder.toString();
        Assert.assertEquals("Expected empty string", "", string);
    }
}
