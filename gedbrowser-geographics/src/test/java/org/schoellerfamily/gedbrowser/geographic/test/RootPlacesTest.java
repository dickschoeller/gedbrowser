package org.schoellerfamily.gedbrowser.geographic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.geographics.RootPlaces;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObject;
import org.schoellerfamily.gedbrowser.reader.ReaderHelper;

/**
 * @author Dick Schoeller
 */
public class RootPlacesTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Converts AbstractGedLine hierarchy to GedObject hierarchy.
     */
    private final transient GedLineToGedObject g2g = new GedLineToGedObject();

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
        final Root root = g2g.create(top);
        final Places rootPlaces = new RootPlaces(root);
        final Collection<Place> places = rootPlaces.getPlaces();
        final int expectedSize = 18;
        assertEquals("Size mismatch", expectedSize, places.size());
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
        final Root root = g2g.create(top);
        final Places rootPlaces = new RootPlaces(root);
        final Collection<Place> places = rootPlaces.getPlaces();
        dump(places);
        assertTrue("Always pass", tRue());
    }

    /**
     * @return true
     */
    private boolean tRue() {
        return true;
    }

    /**
     * Dump the collection to standard output for "logging".
     *
     * @param places the collection to dump
     */
    protected final void dump(final Collection<Place> places) {
        logger.info("A total of " + places.size() + " distinct places");
        for (final Place place : places) {
            logger.info("    " + place.getString());
        }
    }
}
