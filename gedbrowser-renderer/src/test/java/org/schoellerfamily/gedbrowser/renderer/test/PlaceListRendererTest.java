package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class PlaceListRendererTest {
    /** */
    @Autowired
    private transient GeoServiceClient client;

    /**
     * Setup the configurations for this test class.
     *
     * @author Dick Schoeller
     */
    @Configuration
    static class ContextConfiguration {
        /**
         * @return the persistence manager
         */
        @Bean
        public GeoServiceClient geoServiceClient() {
            return new GeoServiceClientStub();
        }
    }

    /** */
    @Test
    public final void testNullArgs() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, null);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public final void testNullClient() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public final void testNullPerson() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, client);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public final void testNoAttributes() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public final void testNoPlaces() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", "20 JAN 2017");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public final void testOnePlace() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain 1 item", 1, list.size());
    }

    /** */
    @Test
    public final void testOnePlaceIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain Needham",
                "Needham, Massachusetts, USA", list.get(0).getPlaceName());
    }

    /** */
    @Test
    public final void testOnePlaceLatitudeIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(42.2809285);
        final Double actual = list.get(0).getLatitude();
        assertEquals("Should contain Needham's latitude", expected, actual);
    }

    /** */
    @Test
    public final void testOnePlaceLongitudeIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(-71.2377548);
        final Double actual = list.get(0).getLongitude();
        assertEquals("Should contain Needham's longitude", expected, actual);
    }

    /** */
    @Test
    public final void testOnePlaceIsNotFound() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should be empty", list.isEmpty());
    }

    /** */
    @Test
    public final void testOnePlaceIsNotFoundAnotherIs() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        createDeath(builder, person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /**
     * @param builder the builder that helps out here
     * @param person the person
     * @param placeName the place of birth
     */
    private void createBirth(final GedObjectBuilder builder,
            final Person person, final String placeName) {
        final Attribute birth =
                builder.createPersonEvent(person, "Birth", "20 JAN 2017");
        final Place place = new Place(birth, placeName);
        birth.insert(place);
    }

    /**
     * @param builder the builder that helps out here
     * @param person the person
     * @param placeName the place of death
     */
    private void createDeath(final GedObjectBuilder builder,
            final Person person, final String placeName) {
        final Attribute birth =
                builder.createPersonEvent(person, "Death", "20 JAN 2017");
        final Place place = new Place(birth, placeName);
        birth.insert(place);
    }
}
