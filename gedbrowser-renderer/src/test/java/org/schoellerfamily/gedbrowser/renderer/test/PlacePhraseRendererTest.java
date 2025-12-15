package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.PlacePhraseRenderer;
import org.schoellerfamily.gedbrowser.renderer.PlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class PlacePhraseRendererTest {
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
    @BeforeEach
    public void init() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson();
        attribute = new Attribute(person, "String", "");
        person.addAttribute(attribute);
        place = new Place(attribute, "Fayetteville, NC");
        attribute.addAttribute(place);
        anonymousContext = RenderingContext.anonymous(appInfo);
    }

    /** */
    @Test
    public void testGetRenderAsPhrase() {
        final PlaceRenderer dRenderer = new PlaceRenderer(place,
                new GedRendererFactory(), anonymousContext);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("Fayetteville, NC", string, "Rendered string doesn't match expectation");
    }

    /** */
    @Test
    public void testGetRenderAsListItemEmpty() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, ""), new GedRendererFactory(),
                anonymousContext);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("", string, "Expected empty string");
    }

    /** */
    @Test
    public void testGetRenderAsListItemNull() {
        final PlaceRenderer dRenderer = new PlaceRenderer(
                new Place(attribute, null), new GedRendererFactory(),
                anonymousContext);
        final PlacePhraseRenderer ppRenderer =
                (PlacePhraseRenderer) dRenderer.getPhraseRenderer();
        final String string = ppRenderer.renderAsPhrase();
        assertEquals("", string, "Expected empty string");
    }
}
