package org.schoellerfamily.gedbrowser.geographic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.PersonPlaces;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public final class PersonPlacesTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedObjectCreator g2g;

    /**
     * Test against the known data for Arnold Robinson.
     * TODO Replace this with something from sanitized data.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public void testArnold() throws IOException {
        final AbstractGedLine top =
                TestResourceReader.readFileTestSource(this,
                        "mini-schoeller.ged");
        final Root root = g2g.create(top);
        final Person person = (Person) root.find("I7");
        final PersonPlaces personPlaces = new PersonPlaces(person);
        final Collection<Place> places = personPlaces.getPlaces();
        assertArnoldMatches(places);
        dump(places);
    }

    /**
     * @param places Arnold's places
     */
    private void assertArnoldMatches(final Collection<Place> places) {
        final int expectedSize = 3;
        assertEquals("Place list size mismatch",
                expectedSize, places.size());
        // This is known to be in the data twice
        assertTrue("Expected to find place",
                places.contains(
                new Place(null,
                        "Providence,"
                        + " Providence County,"
                        + " Rhode Island,"
                        + " USA")));
        assertTrue("Expected to find place",
                places.contains(
                new Place(null,
                        "Lincoln Park Cemetery,"
                        + " Warwick,"
                        + " Kent County,"
                        + " Rhode Island,"
                        + " USA")));
        // Verifies picking up marriage location
        assertTrue("Expected to find place",
                places.contains(
                new Place(null,
                        "Beacon House,"
                        + " Boston,"
                        + " Suffolk County,"
                        + " Massachusetts,"
                        + " USA")));
    }

    /**
     * Test against the known data for Dick Schoeller.
     * TODO Replace this with something from sanitized data.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public void testDick() throws IOException {
        final AbstractGedLine top =
                TestResourceReader.readFileTestSource(this,
                        "mini-schoeller.ged");
        final Root root = g2g.create(top);
        final Person person = (Person) root.find("I2");
        final Places personPlaces = new PersonPlaces(person);
        final Collection<Place> places = personPlaces.getPlaces();
        assertDickMatches(places);
        dump(places);
    }

    /**
     * @param places Dick's places
     */
    private void assertDickMatches(final Collection<Place> places) {
        final int expectedSize = 3;
        assertEquals("Place list size mismatch",
                expectedSize, places.size());
        assertTrue("Expected to find place",
                places.contains(
                new Place(null,
                        "Womack Army Hospital,"
                        + " Fort Bragg,"
                        + " Manchester,"
                        + " Cumberland County,"
                        + " North Carolina,"
                        + " USA")));
        // Verifies picking up marriage location
        assertTrue("Expected to find place",
                places.contains(
                new Place(null,
                        "Temple Emanu-el,"
                        + " Providence,"
                        + " Providence County,"
                        + " Rhode Island,"
                        + " USA")));
    }

    /**
     * Dump the collection to standard output for "logging".
     *
     * @param places the collection to dump
     */
    private void dump(final Collection<Place> places) {
        logger.info("A total of " + places.size() + " distinct places");
        for (final Place place : places) {
            logger.info("    " + place.getString());
        }
    }
}
