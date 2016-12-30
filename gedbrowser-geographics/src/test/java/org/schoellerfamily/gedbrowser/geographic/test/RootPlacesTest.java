package org.schoellerfamily.gedbrowser.geographic.test;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.geographics.RootPlaces;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

import org.junit.Assert;

/**
 * @author Dick Schoeller
 */
public class RootPlacesTest {
    /**
     * Right now, reads and dumps. Just checking for apparent
     * duplicates. Will take further eventually.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public final void testRootPlacesMini() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this, "mini-schoeller.ged");
        final Root root = (Root) top.createGedObject((AbstractGedLine) null);
        final Places rootPlaces = new RootPlaces(root);
        final Collection<Place> places = rootPlaces.getPlaces();
        final int expectedSize = 18;
        Assert.assertEquals(expectedSize, places.size());
        dump(places);
    }

    /**
     * Right now, reads and dumps. Just checking for apparent
     * duplicates. Will take further eventually.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public final void testRootPlacesFull() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this, "gl120368.ged");
        final Root root = (Root) top.createGedObject((AbstractGedLine) null);
        final Places rootPlaces = new RootPlaces(root);
        final Collection<Place> places = rootPlaces.getPlaces();
        dump(places);
        Assert.assertTrue("Always pass", true);
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
