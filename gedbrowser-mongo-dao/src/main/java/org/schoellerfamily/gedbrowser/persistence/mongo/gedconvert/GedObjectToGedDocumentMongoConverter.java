package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.NoteLink;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.SubmissionLink;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.SubmitterLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NoteLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmissionLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmitterLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.TrailerDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.ExcessiveImports" })
public final class GedObjectToGedDocumentMongoConverter
        implements GedDocumentLoader {
    /**
     * Holds the mapping between GedObject and GedDocument.
     */
    private static final Map<Class<? extends GedObject>,
        Class<? extends GedDocumentMongo<? extends GedObject>>> CLASS_MAP =
            new HashMap<>();
    static {
        CLASS_MAP.put(Attribute.class, AttributeDocumentMongo.class);
        CLASS_MAP.put(Child.class, ChildDocumentMongo.class);
        CLASS_MAP.put(Date.class, DateDocumentMongo.class);
        CLASS_MAP.put(Name.class, NameDocumentMongo.class);
        CLASS_MAP.put(Family.class, FamilyDocumentMongo.class);
        CLASS_MAP.put(FamC.class, FamCDocumentMongo.class);
        CLASS_MAP.put(FamS.class, FamSDocumentMongo.class);
        CLASS_MAP.put(Head.class, HeadDocumentMongo.class);
        CLASS_MAP.put(Husband.class, HusbandDocumentMongo.class);
        CLASS_MAP.put(Multimedia.class, MultimediaDocumentMongo.class);
        CLASS_MAP.put(Note.class, NoteDocumentMongo.class);
        CLASS_MAP.put(NoteLink.class, NoteLinkDocumentMongo.class);
        CLASS_MAP.put(Person.class, PersonDocumentMongo.class);
        CLASS_MAP.put(Place.class, PlaceDocumentMongo.class);
        CLASS_MAP.put(Root.class, RootDocumentMongo.class);
        CLASS_MAP.put(Source.class, SourceDocumentMongo.class);
        CLASS_MAP.put(SourceLink.class, SourceLinkDocumentMongo.class);
        CLASS_MAP.put(Submission.class, SubmissionDocumentMongo.class);
        CLASS_MAP.put(SubmissionLink.class, SubmissionLinkDocumentMongo.class);
        CLASS_MAP.put(Submitter.class, SubmitterDocumentMongo.class);
        CLASS_MAP.put(SubmitterLink.class, SubmitterLinkDocumentMongo.class);
        CLASS_MAP.put(Trailer.class, TrailerDocumentMongo.class);
        CLASS_MAP.put(Wife.class, WifeDocumentMongo.class);
    }

    /**
     * Constructor.
     */
    public GedObjectToGedDocumentMongoConverter() {
        // Empty
    }

    /**
     * @param ged the GedObject that we are going to persist
     * @return the mongo document to represent it
     */
    private GedDocumentMongo<? extends GedObject> create(final GedObject ged) {
        if (ged == null) {
            throw new PersistenceException(
                    "Null ged object not supported");
        }
        final Class<? extends GedDocumentMongo<?>> mongoClass =
                CLASS_MAP.get(ged.getClass());
        if (mongoClass == null) {
            throw new PersistenceException("Class not supported");
        }
        try {
            final GedDocumentMongo<?> retval = mongoClass.newInstance();
            retval.loadGedObject(this, ged);
            return retval;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PersistenceException(
                    "Could not instantiate class", e);
        }
    }

    /**
     * @param document the document
     * @param gedAttributes the attributes to add
     */
    public void loadAttributes(final GedDocument<?> document,
            final List<GedObject> gedAttributes) {
        document.clearAttributes();
        for (final GedObject ged : gedAttributes) {
            final GedDocument<?> documentAttribute =
                    createGedDocument(ged);
            document.addAttribute(documentAttribute);
        }
    }

    /**
     * @param <G> type of GedObject provided
     * @param ged the GedObject that is being transformed
     * @return the mongo document produced
     */
    public <G extends GedObject> GedDocumentMongo<G> createGedDocument(
            final G ged) {
        @SuppressWarnings("unchecked")
        final GedDocumentMongo<G> retval = (GedDocumentMongo<G>) create(ged);
        retval.setGedObject(ged);
        return retval;
    }
}
