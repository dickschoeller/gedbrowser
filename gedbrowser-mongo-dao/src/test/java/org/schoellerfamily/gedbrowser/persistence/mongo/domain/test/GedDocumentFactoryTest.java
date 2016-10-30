package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

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
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;

/**
 * @author Dick Schoeller
 */
public class GedDocumentFactoryTest { // NOPMD
    /** */
    @Test
    public final void testCreateAttributeDocument() {
        final GedObject ged = new Attribute(null);
        final String typeString = "attribute";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<AttributeDocumentMongo> expectedClass =
                AttributeDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateChildDocument() {
        final GedObject ged = new Child(null);
        final String typeString = "child";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<ChildDocumentMongo> expectedClass =
                ChildDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateDateDocument() {
        final GedObject ged = new Date(null);
        final String typeString = "date";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<DateDocumentMongo> expectedClass =
                DateDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateMultimediaDocument() {
        final GedObject ged = new Multimedia(null);
        final String typeString = "multimedia";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<MultimediaDocumentMongo> expectedClass =
                MultimediaDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateNameDocument() {
        final GedObject ged = new Name(null);
        final String typeString = "name";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<NameDocumentMongo> expectedClass =
                NameDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamilyDocument() {
        final GedObject ged = new Family(null);
        final String typeString = "family";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamilyDocumentMongo> expectedClass =
                FamilyDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamCDocument() {
        final GedObject ged = new FamC(null);
        final String typeString = "famc";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamCDocumentMongo> expectedClass =
                FamCDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateFamSDocument() {
        final GedObject ged = new FamS(null);
        final String typeString = "fams";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<FamSDocumentMongo> expectedClass =
                FamSDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateHeadDocument() {
        final GedObject ged = new Head(null);
        final String typeString = "head";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<HeadDocumentMongo> expectedClass =
                HeadDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateHusbandDocument() {
        final GedObject ged = new Husband(null);
        final String typeString = "husband";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<HusbandDocumentMongo> expectedClass =
                HusbandDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreatePersonDocument() {
        final GedObject ged = new Person(null);
        final String typeString = "person";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<PersonDocumentMongo> expectedClass =
                PersonDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreatePlaceDocument() {
        final GedObject ged = new Place(null);
        final String typeString = "place";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<PlaceDocumentMongo> expectedClass =
                PlaceDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSourceDocument() {
        final GedObject ged = new Source(null);
        final String typeString = "source";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceDocumentMongo> expectedClass =
                SourceDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSourceLinkDocument() {
        final GedObject ged = new SourceLink(null);
        final String typeString = "sourcelink";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SourceLinkDocumentMongo> expectedClass =
                SourceLinkDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSubmittorDocument() {
        final GedObject ged = new Submittor(null);
        final String typeString = "submittor";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorDocumentMongo> expectedClass =
                SubmittorDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateSubmittorLinkDocument() {
        final GedObject ged = new SubmittorLink(null);
        final String typeString = "submittorlink";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<SubmittorLinkDocumentMongo> expectedClass =
                SubmittorLinkDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateTrailerDocument() {
        final GedObject ged = new Trailer(null);
        final String typeString = "trailer";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<TrailerDocumentMongo> expectedClass =
                TrailerDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testCreateWifeDocument() {
        final GedObject ged = new Wife(null);
        final String typeString = "wife";
        final GedDocument<?> gmd = GedDocumentMongoFactory.getInstance()
                .createGedDocument(ged);
        final Class<WifeDocumentMongo> expectedClass =
                WifeDocumentMongo.class;
        assertTrue(checkGedDocument(ged, gmd, typeString, expectedClass));
    }

    /** */
    @Test
    public final void testNullCreateDocument() {
        GedDocument<?> gmd = null;
        try {
            gmd = GedDocumentMongoFactory.getInstance().
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
            gmd = GedDocumentMongoFactory.getInstance().
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
            final Class<? extends GedDocumentMongo<?>> expectedClass) {
        assertEquals(gedDocument.getClass(), expectedClass);
        assertEquals(ged.getString(), gedDocument.getString());
        assertEquals(expectedTypeString, gedDocument.getType());
        return true;
    }

    /** */
    @Test
    public final void testCreateAttribute() {
        final AttributeDocumentMongo gmd = new AttributeDocumentMongo();
        gmd.setString("Attribute");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Attribute.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateChild() {
        final ChildDocumentMongo gmd = new ChildDocumentMongo();
        gmd.setString("Child");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Child.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateDate() {
        final DateDocumentMongo gmd = new DateDocumentMongo();
        gmd.setString("Date");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Date.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamily() {
        final FamilyDocumentMongo gmd = new FamilyDocumentMongo();
        gmd.setString("Family");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Family.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamC() {
        final FamCDocumentMongo gmd = new FamCDocumentMongo();
        gmd.setString("FamC");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(FamC.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateFamS() {
        final FamSDocumentMongo gmd = new FamSDocumentMongo();
        gmd.setString("FamS");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(FamS.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateHead() {
        final HeadDocumentMongo gmd = new HeadDocumentMongo();
        gmd.setString("Head");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Head.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateHusband() {
        final HusbandDocumentMongo gmd = new HusbandDocumentMongo();
        gmd.setString("Husband");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Husband.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreatePerson() {
        final PersonDocumentMongo gmd = new PersonDocumentMongo();
        gmd.setString("Person");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Person.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreatePlace() {
        final PlaceDocumentMongo gmd = new PlaceDocumentMongo();
        gmd.setString("Place");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Place.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSource() {
        final SourceDocumentMongo gmd = new SourceDocumentMongo();
        gmd.setString("Source");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Source.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSourceLink() {
        final SourceLinkDocumentMongo gmd = new SourceLinkDocumentMongo();
        gmd.setString("SourceLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(SourceLink.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSubmittor() {
        final SubmittorDocumentMongo gmd = new SubmittorDocumentMongo();
        gmd.setString("Submittor");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Submittor.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateSubmittorLink() {
        final SubmittorLinkDocumentMongo gmd = new SubmittorLinkDocumentMongo();
        gmd.setString("SubmittorLink");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(SubmittorLink.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateTrailer() {
        final TrailerDocumentMongo gmd = new TrailerDocumentMongo();
        gmd.setString("Trailer");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Trailer.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateWife() {
        final WifeDocumentMongo gmd = new WifeDocumentMongo();
        gmd.setString("Wife");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Wife.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateMultimedia() {
        final MultimediaDocumentMongo gmd = new MultimediaDocumentMongo();
        gmd.setString("Multimedia");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Multimedia.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateName() {
        final NameDocumentMongo gmd = new NameDocumentMongo();
        gmd.setString("Name");
        final GedObject ged = GedDocumentMongoFactory.getInstance().
                createGedObject(new Root(null), gmd);
        assertEquals(Name.class, ged.getClass());
    }

    /** */
    @Test
    public final void testCreateGed() {
        final GedDocument<?> gmd = new GedDocumentMongo<GedObject>() {
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
            ged = GedDocumentMongoFactory.getInstance().
                    createGedObject(new Root(null), gmd);
            fail("Should not get here");
        } catch (PersistenceException e) {
            assertNull(ged);
        }
    }
}
