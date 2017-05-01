package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import java.util.HashMap;
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
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.CouplingBetweenObjects")
public final class GedDocumentMongoFactory {
    /** */
    private static final GedDocumentMongoFactory INSTANCE =
            new GedDocumentMongoFactory();

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
        CLASS_MAP.put(Person.class, PersonDocumentMongo.class);
        CLASS_MAP.put(Place.class, PlaceDocumentMongo.class);
        CLASS_MAP.put(Root.class, RootDocumentMongo.class);
        CLASS_MAP.put(Source.class, SourceDocumentMongo.class);
        CLASS_MAP.put(SourceLink.class, SourceLinkDocumentMongo.class);
        CLASS_MAP.put(Submittor.class, SubmittorDocumentMongo.class);
        CLASS_MAP.put(SubmittorLink.class, SubmittorLinkDocumentMongo.class);
        CLASS_MAP.put(Trailer.class, TrailerDocumentMongo.class);
        CLASS_MAP.put(Wife.class, WifeDocumentMongo.class);
    }

    /**
     * Constructor.
     */
    private GedDocumentMongoFactory() {
    }

    /**
     * @return the singleton
     */
    public static GedDocumentMongoFactory getInstance() {
        return INSTANCE;
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
            retval.loadGedObject(ged);
            return retval;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PersistenceException(
                    "Could not instantiate class", e);
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

    /**
     * @param parent the parent object
     * @param document the document to transform
     * @return the transformation result
     */
    public GedObject createGedObject(final GedObject parent,
            final GedDocument<?> document) {
        if (document == null) {
            throw new PersistenceException("whoops: null document");
        }

        final GedDocumentMongo<? extends GedObject> documentMongo =
                (GedDocumentMongo<? extends GedObject>) document;
        final GedObjectCreatorVisitor visitor =
                new GedObjectCreatorVisitor(parent);
        documentMongo.accept(visitor);
        final GedObject retval = visitor.getGedObject();

        for (final GedDocument<?> subDocument : document.getAttributes()) {
            final GedObject subGed = createGedObject(retval, subDocument);
            retval.addAttribute(subGed);
        }
        return retval;
    }

    /**
     * @param document the repository document for this root
     * @param finder the repository finder instance
     * @return the root object.
     */
    public Root createRoot(final RootDocument document,
            final FinderStrategy finder) {
        final Root root = new Root("Root", finder);
        root.setFilename(document.getFilename());
        root.setDbName(document.getDbName());
        return root;
    }
}
