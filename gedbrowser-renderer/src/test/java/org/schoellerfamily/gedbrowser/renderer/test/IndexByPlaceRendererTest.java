package org.schoellerfamily.gedbrowser.renderer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;



/**
 * Contains tests for index by place renderer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@Slf4j
final class IndexByPlaceRendererTest {

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

    @BeforeEach
    void setUp() {
        anonymousContext = RenderingContext.anonymous(appInfo);
        userContext = RenderingContext.user(appInfo);
        final UserImpl admin = new UserImpl();
        admin.setUsername("admin");
        admin.addRole("USER");
        admin.addRole("ADMIN");
        adminContext = new RenderingContext(admin, appInfo, provider);
    }

    @Test
    void testIndexAsAnon() throws IOException {
        // Living check is too slow. Turned off display
        // for anonymous user.
        final int[] sizes = {
//                1, 1, 1,
        };
        assertRenderMatches(sizes, anonymousContext);
    }

    @Test
    void testIndexAsUser() throws IOException {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final int[] sizes = { 2, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 2, 1, };
        assertRenderMatches(sizes, userContext);
    }

    @Test
    void testIndexAsAdmin() throws IOException {
        @SuppressWarnings("checkstyle:nowhitespaceafter")
        final int[] sizes = { 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 2, 2, 1, };
        assertRenderMatches(sizes, adminContext);
    }

    @Test
    void testIndexAsAdminSchoeller() throws IOException {
        // Test can only be run with my data.
        // Takes about .4 seconds
        final Root root = reader.readFileTestSource("schoeller.ged");
        log.info("starting testIndexAsAdminSchoeller");
        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(root, client, adminContext);
        final Map<String, Set<PersonRenderer>> map = ir.getWholeIndex();
        log.info("schoeller.ged contains {} places", map.size());
        for (final Map.Entry<String, Set<PersonRenderer>> entry : map.entrySet()) {
            log.info(entry.getKey());
            for (final PersonRenderer person : entry.getValue()) {
                log.info("    {}", person.getIndexName());
            }
        }
        log.info("done testIndexAsAdminSchoeller");
        final int expected = 1233;
        assertEquals(expected, map.size(), "maps size wrong");
    }

    private Person createJRandom() {
        return builder.createPerson("I1", "J. Random/Schoeller/");
    }

    @Test
    void testIndexAsAdminSchoellerPlaceInfo() throws IOException {
        // Have to build what the stub client can deal with.
        // Stub still doesn't return enough interesting things to work on
        // better algorithms.
        final Person person = createJRandom();
        builder.createPersonEvent(person, "Death", "20 JAN 2017");
        final Person person2 = builder.createPerson("I2", "Anonymous/Schoeller/");
        final Family family = builder.createFamily("F1");
        builder.addHusbandToFamily(family, person);
        builder.addWifeToFamily(family, person2);
        final Attribute marriage = builder.createFamilyEvent(family, "Marriage", "21 DEC 2016");
        builder.addPlaceToEvent(marriage, "Needham, Massachusetts, USA");

        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(builder.getRoot(), client,
            adminContext);
        final Map<GeoServiceItem, Set<PersonRenderer>> map = ir.render();
        System.out.println("dummy contains " + map.size() + " places");
        for (final Map.Entry<GeoServiceItem, Set<PersonRenderer>> e : map.entrySet()) {
            final GeoServiceItem key = e.getKey();
            final Set<PersonRenderer> value = e.getValue();
            System.out.println(key.getPlaceName());
            for (final PersonRenderer p : value) {
                System.out.println("    " + p.getIndexName());
            }
        }
        assertEquals(1, map.size(), "map is empty");
    }

    private void assertRenderMatches(final int[] sizes, final RenderingContext context)
        throws IOException {
        final Root root = reader.readBigTestSource();
        final IndexByPlaceRenderer ir = new IndexByPlaceRenderer(root, client, context);
        final Map<String, Set<PersonRenderer>> map = ir.getWholeIndex();
        final int expectedPlaceCount = sizes.length;
        assertEquals(expectedPlaceCount, map.size(), "Number of places doesn't match");
        int i = 0;
        for (final Map.Entry<String, Set<PersonRenderer>> entry : map.entrySet()) {
            assertEquals(sizes[i++], entry.getValue().size(),
                "Person count for place: " + entry.getKey() + " mismatch");
        }
    }
}
