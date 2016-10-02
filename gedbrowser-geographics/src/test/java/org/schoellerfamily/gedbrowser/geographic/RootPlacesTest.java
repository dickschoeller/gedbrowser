package org.schoellerfamily.gedbrowser.geographic;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

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
    public final void testRootPlaces() throws IOException {
        final AbstractGedLine top =
                ReaderHelper.readFileTestSource(this,
                        "/var/lib/gedbrowser/schoeller.ged");
        final Root root = (Root) top.createGedObject((AbstractGedLine) null);
        Places rootPlaces = new RootPlaces(root);
        Collection<Place> places = rootPlaces.getPlaces();
        dump(places);
    }

    /**
     * Dump the collection to standard output for "logging".
     *
     * @param places the collection to dump
     */
    protected final void dump(final Collection<Place> places) {
        System.out.println("A total of " + places.size() + " distinct places");
        for (Place place : places) {
            System.out.println("    " + place.getString());
        }
    }
}
