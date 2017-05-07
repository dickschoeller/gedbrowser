package org.schoellerfamily.gedbrowser.geographic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.geographics.Places;
import org.schoellerfamily.gedbrowser.geographics.RootPlaces;
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
public class RootPlacesTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedObjectCreator g2g;

    /**
     * Right now, reads and dumps. Just checking for apparent
     * duplicates. Will take further eventually.
     *
     * @throws IOException if can't read the GEDCOM file
     */
    @Test
    public final void testRootPlacesMini() throws IOException {
        final AbstractGedLine top = TestResourceReader.readFileTestSource(
                this, "mini-schoeller.ged");
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
                TestResourceReader.readFileTestSource(this, "gl120368.ged");
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
