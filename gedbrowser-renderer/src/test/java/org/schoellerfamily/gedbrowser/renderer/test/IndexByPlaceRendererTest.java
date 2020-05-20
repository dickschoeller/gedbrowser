package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.analytics.calendar.CalendarProvider;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.util.GedObjectBuilder;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.schoellerfamily.gedbrowser.renderer.IndexByPlaceRenderer;
import org.schoellerfamily.gedbrowser.renderer.PersonRenderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.schoellerfamily.geoservice.client.GeoServiceClient;
import org.schoellerfamily.geoservice.model.GeoServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class IndexByPlaceRendererTest {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient TestDataReader reader;
    /** */
    @Autowired
    private transient ApplicationInfo appInfo;
    /** */
    @Autowired
    private transient CalendarProvider provider;
    /** */
    @Autowired
    private transient GeoServiceClient client;

    /** */
    private final GedObjectBuilder builder = new GedObjectBuilder();

    /** */
    private RenderingContext anonymousContext;

    /** */
    private RenderingContext userContext;

    /** */
    private RenderingContext adminContext;

    /** */
    @Before
    public void init() {
        anonymousContext = RenderingContext.anonymous(appInfo);
        userContext = RenderingContext.user(appInfo);
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("USER");
        admin.addRole("ADMIN");
        adminContext = new RenderingContext(admin, appInfo, provider);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexAsAnon() throws IOException {
        // Living check is too slow. Turned off display
        // for anonymous user.
        final int[] sizes = {
//                1, 1, 1,
        };
        assertRenderMatches(sizes, anonymousContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexAsUser() throws IOException {
        final int[] sizes = {
                2, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 2, 1,
        };
        assertRenderMatches(sizes, userContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexAsAdmin() throws IOException {
        final int[] sizes = {
                2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 2, 1,
        };
        assertRenderMatches(sizes, adminContext);
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    @Ignore
    public void testIndexAsAdminSchoeller() throws IOException {
        // Test can only be run with my data.
        // Takes about .4 seconds
        final Root root = reader.readFileTestSource(
                "/var/lib/gedbrowser/schoeller.ged");
        logger.info("starting testIndexAsAdminSchoeller");
        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(root,
                client, adminContext);
        final Map<String, Set<PersonRenderer>> map = ir.getWholeIndex();
        logger.info("schoeller.ged contains " + map.size() + " places");
        for (final Map.Entry<String, Set<PersonRenderer>> entry
                : map.entrySet()) {
            logger.info(entry.getKey());
            for (final PersonRenderer person : entry.getValue()) {
                logger.info("    " + person.getIndexName());
            }
        }
        logger.info("done testIndexAsAdminSchoeller");
        final int expected = 950;
        assertEquals("maps size wrong", expected, map.size());
    }

    /**
     * A common person creator.
     *
     * @return the person
     */
    private Person createJRandom() {
        return builder.createPerson("I1", "J. Random/Schoeller/");
    }

    /**
     * @throws IOException because the reader can
     */
    @Test
    public void testIndexAsAdminSchoellerPlaceInfo() throws IOException {
        // Have to build what the stub client can deal with.
        // Stub still doesn't return enough interesting things to work on
        // better algorithms.
        final Person person = createJRandom();
        builder.createPersonEvent(
                person, "Death", "20 JAN 2017");
        final Person person2 =
                builder.createPerson("I2", "Anonymous/Schoeller/");
        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person);
        builder.addWifeToFamily(family, person2);
        final Attribute marriage = builder.createFamilyEvent(
                family, "Marriage", "21 DEC 2016");
        builder.addPlaceToEvent(marriage, "Needham, Massachusetts, USA");

        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(
                builder.getRoot(), client, adminContext);
        final Map<GeoServiceItem, Set<PersonRenderer>> map = ir.render();
        System.out.println("dummy contains " + map.size() + " places");
        for (final Map.Entry<GeoServiceItem, Set<PersonRenderer>> e
                : map.entrySet()) {
            final GeoServiceItem key = e.getKey();
            final Set<PersonRenderer> value = e.getValue();
            System.out.println(key.getPlaceName());
            for (final PersonRenderer p : value) {
                System.out.println("    " + p.getIndexName());
            }
        }
        assertEquals("map is empty", 1, map.size());
    }

    /**
     * Do all the work for a specific context.
     *
     * @param sizes the sizes
     * @param context the context
     * @throws IOException if file can't be read
     */
    private void assertRenderMatches(final int[] sizes,
            final RenderingContext context) throws IOException {
        final Root root = reader.readBigTestSource();
        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(root,
                client,
                context);
        final Map<String, Set<PersonRenderer>> map = ir.getWholeIndex();
        final int expectedPlaceCount = sizes.length;
        assertEquals("Number of places doesn't match", expectedPlaceCount,
                map.size());
        int i = 0;
        for (final Map.Entry<String, Set<PersonRenderer>> entry
                : map.entrySet()) {
            assertEquals(
                    "Person count for place: " + entry.getKey() + " mismatch",
                    sizes[i++], entry.getValue().size());
        }
    }
}
