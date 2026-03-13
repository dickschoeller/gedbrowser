package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
@SuppressWarnings({ "PMD.TooManyMethods" })
final class PlaceListRendererTest {
    /** */
    @Autowired
    private transient GeoServiceClient client;
    /** */
    @Autowired
    private CalendarProvider provider;
    /** */
    @Autowired
    private ApplicationInfo appInfo;

    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /**
     * A common person creator.
     *
     * @return the person
     */
    private Person createJRandom() {
        return builder.createPerson("I1", "J. Random/Schoeller/");
    }

    /**
     * Setup the configurations for this test class.
     *
     * @author Dick Schoeller
     */
    @Configuration
    public static class ContextConfiguration {
    }

    /** */
    @Test
    void testNullArgsUser() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, null,
            RenderingContext.user(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNullArgsAdmin() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, null, createAdminContext());
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNullClient() {
        final Person person = createJRandom();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null,
            RenderingContext.user(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNullClientAdmin() {
        final Person person = createJRandom();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null, createAdminContext());
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNullPerson() {
        final PlaceListRenderer plr = createAdminRenderer(null);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNoAttributes() {
        final Person person = createJRandom();
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testNoPlaces() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Birth", "20 JAN 2017");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should cleanly provide an empty list");
    }

    /** */
    @Test
    void testOnePlace() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should contain 1 item");
    }

    /** */
    @Test
    void testOnePlaceIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Needham, Massachusetts, USA", list.get(0).getPlaceName(),
            "Should contain Needham");
    }

    /** */
    @Test
    void testOnePlaceLatitudeIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(42.2809285);
        final Double actual = list.get(0).getLocation().getLatitude();
        assertEquals(expected, actual, "Should contain Needham's latitude");
    }

    /** */
    @Test
    void testOnePlaceLongitudeIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(-71.2377548);
        final Double actual = list.get(0).getLocation().getLongitude();
        assertEquals(expected, actual, "Should contain Needham's longitude");
    }

    /** */
    @Test
    void testOnePlaceIsNotFound() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should be empty");
    }

    /** */
    @Test
    void testOnePlaceIsNotFoundAnotherIs() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testNullGeoServiceItemIsFiltered() {
        final Person person = createJRandom();
        createBirth(person, "Null Item, USA");
        createDeath(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testNullGeometryResultIsFiltered() {
        final Person person = createJRandom();
        createBirth(person, "Geometry Null, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should be empty");
    }

    /** */
    @Test
    void testPolygonOnlyResultIsFiltered() {
        final Person person = createJRandom();
        createBirth(person, "Polygon Only, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue(list.isEmpty(), "Should be empty");
    }

    /** */
    @Test
    void testAdminCanSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr = builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client, createAdminContext());
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testUserCanNotSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr = builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.user(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(0, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testAnonCanNotSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr = builder.createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.anonymous(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(0, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testAnonCanNotSeeLiving() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.anonymous(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(0, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testAnonCanSeeDead() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        createDeath(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.anonymous(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should have one result");
    }

    /** */
    @Test
    void testAnonCanSeeDeadAltConstruction() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        createDeath(person, "Needham, MA, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.anonymous(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(2, list.size(), "Should have one result");
    }

    /**
     * @param person the person to render
     * @return the renderer
     */
    private PlaceListRenderer createAdminRenderer(final Person person) {
        return new PlaceListRenderer(person, client, createAdminContext());
    }

    /**
     * @param person    the person
     * @param placeName the place of birth
     */
    private void createBirth(final Person person, final String placeName) {
        final Attribute birth = builder.createPersonEvent(person, "Birth", "20 JAN 2017");
        builder.addPlaceToEvent(birth, placeName);
    }

    /**
     * @param person    the person
     * @param placeName the place of death
     */
    private void createDeath(final Person person, final String placeName) {
        final Attribute death = builder.createPersonEvent(person, "Death", "20 JAN 2017");
        builder.addPlaceToEvent(death, placeName);
    }

    /**
     * @return a rendering context with admin privs
     */
    private RenderingContext createAdminContext() {
        final UserImpl user = new UserImpl();
        user.setUsername("dick");
        user.addRole("USER");
        user.addRole("ADMIN");
        return new RenderingContext(user, appInfo, provider);
    }

    /** */
    @Test
    void testAnonCanSeeDeadMarriage() {
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death", "20 JAN 2017");
        final Person person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person);
        builder.addWifeToFamily(family, person2);
        final Attribute marriage = builder.createFamilyEvent(family, "Marriage", "21 DEC 2016");
        builder.addPlaceToEvent(marriage, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
            RenderingContext.anonymous(appInfo));
        final List<PlaceInfo> list = plr.render();
        assertEquals(1, list.size(), "Should have one result");
    }
}
