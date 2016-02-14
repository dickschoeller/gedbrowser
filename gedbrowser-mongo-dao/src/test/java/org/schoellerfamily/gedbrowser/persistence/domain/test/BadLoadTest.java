package org.schoellerfamily.gedbrowser.persistence.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HusbandDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.NameDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PlaceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;

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
        final AttributeDocument ad = new AttributeDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadChildLoad() {
        final ChildDocument ad = new ChildDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadDateLoad() {
        final DateDocument ad = new DateDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamCLoad() {
        final FamCDocument ad = new FamCDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamilyLoad() {
        final FamilyDocument ad = new FamilyDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadFamSLoad() {
        final FamSDocument ad = new FamSDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadHeadLoad() {
        final HeadDocument ad = new HeadDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadHusbandLoad() {
        final HusbandDocument ad = new HusbandDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadMultimediaLoad() {
        final MultimediaDocument ad = new MultimediaDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadNameLoad() {
        final NameDocument ad = new NameDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadPersonLoad() {
        final PersonDocument ad = new PersonDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadPlaceLoad() {
        final PlaceDocument ad = new PlaceDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadRootLoad() {
        final RootDocument ad = new RootDocument();
        try {
            ad.loadGedObject(attr);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSourceLoad() {
        final SourceDocument ad = new SourceDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSourceLinkLoad() {
        final SourceLinkDocument ad = new SourceLinkDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSubmittorLoad() {
        final SubmittorDocument ad = new SubmittorDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadSubmittorLinkLoad() {
        final SubmittorLinkDocument ad = new SubmittorLinkDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadTrailerLoad() {
        final TrailerDocument ad = new TrailerDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }

    /** */
    @Test
    public void testBadWifeLoad() {
        final WifeDocument ad = new WifeDocument();
        try {
            ad.loadGedObject(root);
            fail("Expected to throw persistence exception");
        } catch (PersistenceException e) {
            assertEquals("Wrong type", e.getMessage());
        }
    }
}
