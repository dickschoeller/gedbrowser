package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonBuilder;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceInfo;
import org.schoellerfamily.gedbrowser.renderer.PlaceListRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public final class PlaceListRendererTest {
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
     * @return get the person builder associated with this test
     */
    private PersonBuilder personBuilder() {
        return builder.getPersonBuilder();
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    private Person createJRandom() {
        return personBuilder().createPerson("I1", "J. Random/Schoeller/");
    }

    /**
     * Setup the configurations for this test class.
     *
     * @author Dick Schoeller
     */
    @Configuration
    static class ContextConfiguration {
    }

    /** */
    @Test
    public void testNullArgsUser() {
        final PlaceListRenderer plr = new PlaceListRenderer(null, null,
                RenderingContext.user(appInfo), provider);
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
        final Person person = createJRandom();
        final PlaceListRenderer plr = new PlaceListRenderer(person, null,
                RenderingContext.user(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNullClientAdmin() {
        final Person person = createJRandom();
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
        final Person person = createJRandom();
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testNoPlaces() {
        final Person person = createJRandom();
        personBuilder().createPersonEvent(person, "Birth", "20 JAN 2017");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should cleanly provide an empty list", list.isEmpty());
    }

    /** */
    @Test
    public void testOnePlace() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain 1 item", 1, list.size());
    }

    /** */
    @Test
    public void testOnePlaceIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should contain Needham",
                "Needham, Massachusetts, USA", list.get(0).getPlaceName());
    }

    /** */
    @Test
    public void testOnePlaceLatitudeIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(42.2809285);
        final Double actual = list.get(0).getLocation().getLatitude();
        assertEquals("Should contain Needham's latitude", expected, actual);
    }

    /** */
    @Test
    public void testOnePlaceLongitudeIsNeedham() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        final Double expected = Double.valueOf(-71.2377548);
        final Double actual = list.get(0).getLocation().getLongitude();
        assertEquals("Should contain Needham's longitude", expected, actual);
    }

    /** */
    @Test
    public void testOnePlaceIsNotFound() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertTrue("Should be empty", list.isEmpty());
    }

    /** */
    @Test
    public void testOnePlaceIsNotFoundAnotherIs() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = createAdminRenderer(person);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testAdminCanSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr =
                personBuilder().createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                createAdminContext(), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testUserCanNotSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr =
                personBuilder().createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.user(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanNotSeeConfidential() {
        final Person person = createJRandom();
        createBirth(person, "PLUGH");
        createDeath(person, "Needham, Massachusetts, USA");
        final Attribute attr =
                personBuilder().createPersonEvent(person, "Restriction");
        attr.setTail("confidential");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.user(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanNotSeeLiving() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 0, list.size());
    }

    /** */
    @Test
    public void testAnonCanSeeDead() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        createDeath(person, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }

    /** */
    @Test
    public void testAnonCanSeeDeadAltConstruction() {
        final Person person = createJRandom();
        createBirth(person, "Needham, Massachusetts, USA");
        createDeath(person, "Needham, MA, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(appInfo), provider);
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
     * @param person the person
     * @param placeName the place of birth
     */
    private void createBirth(final Person person, final String placeName) {
        final Attribute birth = personBuilder().createPersonEvent(
                person, "Birth", "20 JAN 2017");
        builder.addPlaceToEvent(birth, placeName);
    }

    /**
     * @param person the person
     * @param placeName the place of death
     */
    private void createDeath(final Person person, final String placeName) {
        final Attribute death = personBuilder().createPersonEvent(
                person, "Death", "20 JAN 2017");
        builder.addPlaceToEvent(death, placeName);
    }

    /**
     * @return a rendering context with admin privs
     */
    private RenderingContext createAdminContext() {
        final User user = new User();
        user.setUsername("dick");
        return new RenderingContext(user, true, true, appInfo);
    }

    /** */
    @Test
    public void testAnonCanSeeDeadMarriage() {
        final Person person = createJRandom();
        builder.getPersonBuilder().createPersonEvent(
                person, "Death", "20 JAN 2017");
        final Person person2 =
                personBuilder().createPerson("I2", "Anonymous/Schoeller/");
        final Family family = builder.getFamilyBuilder().createFamily("F1");
        builder.getFamilyBuilder().addHusbandToFamily(family, person);
        builder.getFamilyBuilder().addWifeToFamily(family, person2);
        final Attribute marriage =
                builder.getFamilyBuilder().createFamilyEvent(
                        family, "Marriage", "21 DEC 2016");
        builder.addPlaceToEvent(marriage, "Needham, Massachusetts, USA");

        final PlaceListRenderer plr = new PlaceListRenderer(person, client,
                RenderingContext.anonymous(appInfo), provider);
        final List<PlaceInfo> list = plr.render();
        assertEquals("Should have one result", 1, list.size());
    }
}
