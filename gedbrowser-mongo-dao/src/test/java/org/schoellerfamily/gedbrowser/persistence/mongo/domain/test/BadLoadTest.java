package org.schoellerfamily.gedbrowser.persistence.mongo.domain.test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.test.MongoTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.ExcessiveImports", "PMD.CouplingBetweenObjects", "PMD.TooManyMethods" })
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { MongoTestConfiguration.class })
final class BadLoadTest {
    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    private final GedObject root = new Root();
    private final GedObject attr = new Attribute(root);

    @Test
    void testBadAttributeLoad() {
        final AttributeDocumentMongo ad = new AttributeDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadChildLoad() {
        final ChildDocumentMongo ad = new ChildDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadDateLoad() {
        final DateDocumentMongo ad = new DateDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadFamCLoad() {
        final FamCDocumentMongo ad = new FamCDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadFamilyLoad() {
        final FamilyDocumentMongo ad = new FamilyDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadFamSLoad() {
        final FamSDocumentMongo ad = new FamSDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadHeadLoad() {
        final HeadDocumentMongo ad = new HeadDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadHusbandLoad() {
        final HusbandDocumentMongo ad = new HusbandDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadMultimediaLoad() {
        final MultimediaDocumentMongo ad = new MultimediaDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadNameLoad() {
        final NameDocumentMongo ad = new NameDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadPersonLoad() {
        final PersonDocumentMongo ad = new PersonDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadPlaceLoad() {
        final PlaceDocumentMongo ad = new PlaceDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadRootLoad() {
        final RootDocumentMongo ad = new RootDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, attr))
            .withMessage("Wrong type");
    }

    @Test
    void testBadSourceLoad() {
        final SourceDocumentMongo ad = new SourceDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadSourceLinkLoad() {
        final SourceLinkDocumentMongo ad = new SourceLinkDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadSubmitterLoad() {
        final SubmitterDocumentMongo ad = new SubmitterDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadSubmitterLinkLoad() {
        final SubmitterLinkDocumentMongo ad = new SubmitterLinkDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadTrailerLoad() {
        final TrailerDocumentMongo ad = new TrailerDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }

    @Test
    void testBadWifeLoad() {
        final WifeDocumentMongo ad = new WifeDocumentMongo();
        assertThatExceptionOfType(PersistenceException.class)
            .isThrownBy(() -> ad.loadGedObject(toDocConverter, root))
            .withMessage("Wrong type");
    }
}
