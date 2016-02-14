package org.schoellerfamily.gedbrowser.persistence.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.ChildDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.DateDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HusbandDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.NameDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PlaceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorLinkDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;

/**
 * @author Dick Schoeller
 */
public class GedDocumentFactoryTest { // NOPMD
    /** */
    @Test
    public final void testCreateAttributeDocument() {
        final GedObject ged = new Attribute(null);
        final String typeString = "attribute";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<AttributeDocument> expectedClass =
                AttributeDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateChildDocument() {
        final GedObject ged = new Child(null);
        final String typeString = "child";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<ChildDocument> expectedClass =
                ChildDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateDateDocument() {
        final GedObject ged = new Date(null);
        final String typeString = "date";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<DateDocument> expectedClass =
                DateDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateMultimediaDocument() {
        final GedObject ged = new Multimedia(null);
        final String typeString = "multimedia";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<MultimediaDocument> expectedClass =
                MultimediaDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateNameDocument() {
        final GedObject ged = new Name(null);
        final String typeString = "name";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<NameDocument> expectedClass =
                NameDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamilyDocument() {
        final GedObject ged = new Family(null);
        final String typeString = "family";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamilyDocument> expectedClass =
                FamilyDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamCDocument() {
        final GedObject ged = new FamC(null);
        final String typeString = "famc";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamCDocument> expectedClass =
                FamCDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamSDocument() {
        final GedObject ged = new FamS(null);
        final String typeString = "fams";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamSDocument> expectedClass =
                FamSDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateHeadDocument() {
        final GedObject ged = new Head(null);
        final String typeString = "head";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<HeadDocument> expectedClass =
                HeadDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateHusbandDocument() {
        final GedObject ged = new Husband(null);
        final String typeString = "husband";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<HusbandDocument> expectedClass =
                HusbandDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreatePersonDocument() {
        final GedObject ged = new Person(null);
        final String typeString = "person";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<PersonDocument> expectedClass =
                PersonDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreatePlaceDocument() {
        final GedObject ged = new Place(null);
        final String typeString = "place";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<PlaceDocument> expectedClass =
                PlaceDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSourceDocument() {
        final GedObject ged = new Source(null);
        final String typeString = "source";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceDocument> expectedClass =
                SourceDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSourceLinkDocument() {
        final GedObject ged = new SourceLink(null);
        final String typeString = "sourcelink";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceLinkDocument> expectedClass =
                SourceLinkDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSubmittorDocument() {
        final GedObject ged = new Submittor(null);
        final String typeString = "submittor";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorDocument> expectedClass =
                SubmittorDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSubmittorLinkDocument() {
        final GedObject ged = new SubmittorLink(null);
        final String typeString = "submittorlink";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorLinkDocument> expectedClass =
                SubmittorLinkDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateTrailerDocument() {
        final GedObject ged = new Trailer(null);
        final String typeString = "trailer";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<TrailerDocument> expectedClass =
                TrailerDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateWifeDocument() {
        final GedObject ged = new Wife(null);
        final String typeString = "wife";
        final GedDocument<?> gmd = GedDocumentFactory.getInstance()
                .createGedDocument(ged);
        final Class<WifeDocument> expectedClass =
                WifeDocument.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testNullCreateDocument() {
        GedDocument<?> gmd = null;
        try {
            gmd = GedDocumentFactory.getInstance().
                    createGedDocument(null);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull(gmd); // Expected
        }
    }

    /** */
    @Test
    public final void testUnexpectedCreateDocument() {
        final GedObject ged = new GedObject(null) {
        };
        GedDocument<?> gmd = null;
        try {
            gmd = GedDocumentFactory.getInstance().
                    createGedDocument(ged);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull(gmd); // Expected
        }
    }

    /**
     * @param ged the ged object that we start with
     * @param gedDocument the mongo document that we end up with
     * @param expectedTypeString the expected type string
     * @param expectedClass the expected class
     * @return true or throws an assertion exception
     */
    private boolean checkGedDocument(final GedObject ged,
            final GedDocument<?> gedDocument, final String expectedTypeString,
            final Class<? extends GedDocument<?>> expectedClass) {
        assertEquals(gedDocument.getClass(), expectedClass);
        assertEquals(ged.getString(), gedDocument.getString());
        assertEquals(expectedTypeString, gedDocument.getType());
        return true;
    }

    /** */
    @Test
    public final void testCreateAttribute() {
        final AttributeDocument gmd = new AttributeDocument();
        gmd.setString("Attribute");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Attribute.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateChild() {
        final ChildDocument gmd = new ChildDocument();
        gmd.setString("Child");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Child.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateDate() {
        final DateDocument gmd = new DateDocument();
        gmd.setString("Date");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Date.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamily() {
        final FamilyDocument gmd = new FamilyDocument();
        gmd.setString("Family");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Family.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamC() {
        final FamCDocument gmd = new FamCDocument();
        gmd.setString("FamC");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(FamC.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamS() {
        final FamSDocument gmd = new FamSDocument();
        gmd.setString("FamS");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(FamS.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateHead() {
        final HeadDocument gmd = new HeadDocument();
        gmd.setString("Head");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Head.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateHusband() {
        final HusbandDocument gmd = new HusbandDocument();
        gmd.setString("Husband");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Husband.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreatePerson() {
        final PersonDocument gmd = new PersonDocument();
        gmd.setString("Person");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Person.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreatePlace() {
        final PlaceDocument gmd = new PlaceDocument();
        gmd.setString("Place");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Place.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSource() {
        final SourceDocument gmd = new SourceDocument();
        gmd.setString("Source");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Source.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSourceLink() {
        final SourceLinkDocument gmd = new SourceLinkDocument();
        gmd.setString("SourceLink");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(SourceLink.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSubmittor() {
        final SubmittorDocument gmd = new SubmittorDocument();
        gmd.setString("Submittor");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Submittor.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSubmittorLink() {
        final SubmittorLinkDocument gmd = new SubmittorLinkDocument();
        gmd.setString("SubmittorLink");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(SubmittorLink.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateTrailer() {
        final TrailerDocument gmd = new TrailerDocument();
        gmd.setString("Trailer");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Trailer.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateWife() {
        final WifeDocument gmd = new WifeDocument();
        gmd.setString("Wife");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Wife.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateMultimedia() {
        final MultimediaDocument gmd = new MultimediaDocument();
        gmd.setString("Multimedia");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Multimedia.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateName() {
        final NameDocument gmd = new NameDocument();
        gmd.setString("Name");
        final GedObject ged = GedDocumentFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Name.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateGed() {
        final GedDocument<?> gmd = new GedDocument<GedObject>() {
            { // NOPMD
                setType("foo");
            }
            @Override
            public void loadGedObject(final GedObject ged) {
            }
        };
        gmd.setString("Foo");
        GedObject ged = null;
        try {
            ged = GedDocumentFactory.getInstance().
                    createGedObject(new Root(null), gmd);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull(ged);
        }
    }
}
