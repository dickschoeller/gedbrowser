package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.CalendarProviderStub;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
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
public final class PlaceListRendererTest {
    /** */
    @Autowired
    private transient GeoServiceClient client;

    /** */
    @Autowired
    private CalendarProvider provider;

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
        // We turn off checkstyle because bean methods must not be final
        // This seems to vary with checkstyle version
        // CHECKSTYLE:OFF
        @Bean
        public GeoServiceClient geoServiceClient() {
            // CHECKSTYLE:ON
            return new GeoServiceClientStub();
        }

        /**
         * @return the calendar provider
         */
        // We turn off checkstyle because bean methods must not be final
        // This seems to vary with checkstyle version
        // CHECKSTYLE:OFF
        @Bean
        public CalendarProvider getCalendarProvider() {
            // CHECKSTYLE:ON
            return new CalendarProviderStub();
        }
    }

    /** */
    @Test
    public void testNullArgsUser() {
        final PlaceListRenderer plr =
                new PlaceListRenderer(null, null, RenderingContext.user(),
                        provider);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNullArgsAdmin() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, null,
                createAdminContext(), provider);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNullClient() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null,
                RenderingContext.user(), provider);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNullClientAdmin() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null,
                createAdminContext(), provider);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNullPerson() {
        final PlaceListRenderer plr = createAdminRenderer(null);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNoAttributes() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNoPlaces() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Birth", "20 JAN 2017");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testOnePlace() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain 1 item", 1, list.size());
    }

    /** */
    @Test
    public void testOnePlaceIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain Needham",
                "Needham, Massachusetts, USA", list.get(0).getPlaceName());
    }

    /** */
    @Test
    public void testOnePlaceLatitudeIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(42.2809285);
        final Double actual = list.get(0).getLocation().getLatitude();
        assertEquals("Should contain Needham's latitude", expected, actual);
    }

    /** */
    @Test
    public void testOnePlaceLongitudeIsNeedham() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(-71.2377548);
        final Double actual = list.get(0).getLocation().getLongitude();
        assertEquals("Should contain Needham's longitude", expected, actual);
    }

    /** */
    @Test
    public void testOnePlaceIsNotFound() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should be empty", list.isEmpty());
    }

    /** */
    @Test
    public void testOnePlaceIsNotFoundAnotherIs() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        createDeath(builder, person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testAdminCanSeeConfidential() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        createDeath(builder, person, "Needham, Massachusetts, USA");
        final Attribute attr =
                builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                createAdminContext(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testUserCanNotSeeConfidential() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        createDeath(builder, person, "Needham, Massachusetts, USA");
        final Attribute attr =
                builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.user(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanNotSeeConfidential() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "PLUGH");
        createDeath(builder, person, "Needham, Massachusetts, USA");
        final Attribute attr =
                builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.user(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanNotSeeLiving() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanSeeDead() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        createDeath(builder, person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testAnonCanSeeDeadAltConstruction() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        createBirth(builder, person, "Needham, Massachusetts, USA");
        createDeath(builder, person, "Needham, MA, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 2, list.size());
    }

    /**
     * @param person the person to render
     * @return the renderer
     */
    private PlaceListRenderer createAdminRenderer(final Person person) {
        return new PlaceListRenderer(person, client, createAdminContext(),
                provider);
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
        builder.addPlaceToEvent(birth, placeName);
    }

    /**
     * @param builder the builder that helps out here
     * @param person the person
     * @param placeName the place of death
     */
    private void createDeath(final GedObjectBuilder builder,
            final Person person, final String placeName) {
        final Attribute death =
                builder.createPersonEvent(person, "Death", "20 JAN 2017");
        builder.addPlaceToEvent(death, placeName);
    }

    /**
     * @return a rendering context with admin privs
     */
    private RenderingContext createAdminContext() {
        final User user = new User();
        user.setUsername("dick");
        return new RenderingContext(user, true, true);
    }

    /** */
    @Test
    public void testAnonCanSeeDeadMarriage() {
        final GedObjectBuilder builder = new GedObjectBuilder();
        final Person person = builder.createPerson1();
        builder.createPersonEvent(person, "Death", "20 JAN 2017");
        final Person person2 = builder.createPerson2();
        final Family family = builder.createFamily1();
        builder.addHusbandToFamily(family, person);
        builder.addWifeToFamily(family, person2);
        final Attribute marriage =
                builder.createFamilyEvent(family, "Marriage", "21 DEC 2016");
        builder.addPlaceToEvent(marriage, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }
}
