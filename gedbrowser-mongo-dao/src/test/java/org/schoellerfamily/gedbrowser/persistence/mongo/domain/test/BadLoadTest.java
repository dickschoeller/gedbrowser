package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;

/**
 * @author Dick Schoeller
 */
public final class BadLoadTest { // NOPMD
    /** */
    private final GedObject root = new Root(null);
    /** */
    private final GedObject attr = new Attribute(root);

    /** */
    @Test
    public void testBadAttributeLoad() {
        final AttributeDocumentMongo ad = new AttributeDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadChildLoad() {
        final ChildDocumentMongo ad = new ChildDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadDateLoad() {
        final DateDocumentMongo ad = new DateDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamCLoad() {
        final FamCDocumentMongo ad = new FamCDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamilyLoad() {
        final FamilyDocumentMongo ad = new FamilyDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamSLoad() {
        final FamSDocumentMongo ad = new FamSDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadHeadLoad() {
        final HeadDocumentMongo ad = new HeadDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadHusbandLoad() {
        final HusbandDocumentMongo ad = new HusbandDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadMultimediaLoad() {
        final MultimediaDocumentMongo ad = new MultimediaDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadNameLoad() {
        final NameDocumentMongo ad = new NameDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadPersonLoad() {
        final PersonDocumentMongo ad = new PersonDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadPlaceLoad() {
        final PlaceDocumentMongo ad = new PlaceDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadRootLoad() {
        final RootDocumentMongo ad = new RootDocumentMongo();
        try {
            ad.loadGedObject(attr);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSourceLoad() {
        final SourceDocumentMongo ad = new SourceDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSourceLinkLoad() {
        final SourceLinkDocumentMongo ad = new SourceLinkDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSubmittorLoad() {
        final SubmittorDocumentMongo ad = new SubmittorDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSubmittorLinkLoad() {
        final SubmittorLinkDocumentMongo ad = new SubmittorLinkDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadTrailerLoad() {
        final TrailerDocumentMongo ad = new TrailerDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadWifeLoad() {
        final WifeDocumentMongo ad = new WifeDocumentMongo();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Expected a wrong type exception",
                    "Wrong type", e.getMessage());
        }
    }
}
