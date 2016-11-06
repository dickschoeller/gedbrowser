package org.schoellerfamily.gedbrowser.geographic.test;

import java.io.IOException;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.PersonPlaces;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public class PersonPlacesTest {
    /**
     * Test against the known data for Arnold Robinson.
     * TODO Replace this with something from sanitized data.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public final void testArnold() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this,
                        "mini-schoeller.ged");
        final Root root = (Root) top.createGedObject((AbstractGedLine) null);
        final Person person = (Person) root.find("I7");
        final PersonPlaces personPlaces = new PersonPlaces(person);
        Collection<Place> places = personPlaces.getPlaces();
        final int expectedSize = 3;
        Assert.assertEquals(expectedSize, places.size());
        // This is known to be in the data twice
        Assert.assertTrue(places.contains(
                new Place(null,
                        "Providence,"
                        + " Providence County,"
                        + " Rhode Island,"
                        + " USA")));
        Assert.assertTrue(places.contains(
                new Place(null,
                        "Lincoln Park Cemetery,"
                        + " Warwick,"
                        + " Kent County,"
                        + " Rhode Island,"
                        + " USA")));
        // Verifies picking up marriage location
        Assert.assertTrue(places.contains(
                new Place(null,
                        "Beacon House,"
                        + " Boston,"
                        + " Suffolk County,"
                        + " Massachusetts,"
                        + " USA")));
        dump(places);
    }

    /**
     * Test against the known data for Dick Schoeller.
     * TODO Replace this with something from sanitized data.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public final void testDick() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this,
                        "mini-schoeller.ged");
        final Root root = (Root) top.createGedObject((AbstractGedLine) null);
        final Person person = (Person) root.find("I2");
        final Places personPlaces = new PersonPlaces(person);
        final Collection<Place> places = personPlaces.getPlaces();
        final int expectedSize = 3;
        Assert.assertEquals(expectedSize, places.size());
        Assert.assertTrue(places.contains(
                new Place(null,
                        "Womack Army Hospital,"
                        + " Fort Bragg,"
                        + " Manchester,"
                        + " Cumberland County,"
                        + " North Carolina,"
                        + " USA")));
        // Verifies picking up marriage location
        Assert.assertTrue(places.contains(
                new Place(null,
                        "Temple Emanu-el,"
                        + " Providence,"
                        + " Providence County,"
                        + " Rhode Island,"
                        + " USA")));
        dump(places);
    }

    /**
     * Dump the collection to standard output for "logging".
     *
     * @param places the collection to dump
     */
    protected final void dump(final Collection<Place> places) {
        System.out.println("A total of " + places.size() + " distinct places");
        for (final Place place : places) {
            System.out.println("    " + place.getString());
        }
    }
}
