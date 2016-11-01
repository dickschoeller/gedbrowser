package org.schoellerfamily.gedbrowser.persistence.mongo.domain; // NOPMD

import java.util.HashMap;
import java.util.Map;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
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
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;

/**
 * @author Dick Schoeller
 */
public final class GedDocumentMongoFactory { // NOPMD
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
    private GedDocument<? extends GedObject> create(final GedObject ged) {
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
            final GedDocument<?> retval = mongoClass.newInstance();
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
    public <G extends GedObject> GedDocument<G> createGedDocument(
            final G ged) {
        @SuppressWarnings("unchecked")
        final GedDocument<G> retval = (GedDocument<G>) create(ged);
        retval.setGedObject(ged);
        return retval;
    }

    /**
     * @param parent the parent object
     * @param document the document to transform
     * @return the transformation result
     */
    public GedObject createGedObject(final GedObject parent, // NOPMD
            final GedDocument<?> document) {
        GedObject retval = null;
        if (document instanceof AttributeDocumentMongo) {
            final AttributeDocumentMongo attributeDocument =
                    (AttributeDocumentMongo) document;
            final Attribute attribute = new Attribute(parent);
            attribute.setString(attributeDocument.getString());
            attribute.setTail(attributeDocument.getTail());
            retval = attribute;
        } else if (document instanceof ChildDocumentMongo) {
            final ChildDocumentMongo childDocument =
                    (ChildDocumentMongo) document;
            final Child child = new Child(parent);
            child.setString("Child");
            child.setFromString(parent.getString());
            child.setToString(childDocument.getString());
            retval = child;
        } else if (document instanceof DateDocumentMongo) {
            retval = new Date(parent, document.getString());
        } else if (document instanceof MultimediaDocumentMongo) {
            final MultimediaDocumentMongo multimediaDocument =
                    (MultimediaDocumentMongo) document;
            final Multimedia attribute = new Multimedia(parent);
            attribute.setString(multimediaDocument.getString());
            attribute.setTail(multimediaDocument.getTail());
            retval = attribute;
        } else if (document instanceof NameDocumentMongo) {
            retval = new Name(parent, document.getString());
        } else if (document instanceof FamilyDocumentMongo) {
            retval = new Family(parent);
            retval.setString(document.getString());
        } else if (document instanceof FamCDocumentMongo) {
            final FamCDocumentMongo famcDocument = (FamCDocumentMongo) document;
            final FamC famc = new FamC(parent);
            famc.setFromString(parent.getString());
            famc.setToString(famcDocument.getString());
            famc.setString("Child of Family");
            retval = famc;
        } else if (document instanceof FamSDocumentMongo) {
            final FamSDocumentMongo famsDocument = (FamSDocumentMongo) document;
            final FamS fams = new FamS(parent);
            fams.setFromString(parent.getString());
            fams.setToString(famsDocument.getString());
            fams.setString("Spouse of Family");
            retval = fams;
        } else if (document instanceof HeadDocumentMongo) {
            retval = new Head(parent);
            retval.setString("Header");
        } else if (document instanceof HusbandDocumentMongo) {
            final HusbandDocumentMongo husbandDocument =
                    (HusbandDocumentMongo) document;
            final Husband husband = new Husband(parent);
            husband.setString("Husband");
            husband.setFromString(parent.getString());
            husband.setToString(husbandDocument.getString());
            retval = husband;
        } else if (document instanceof PersonDocumentMongo) {
            retval = new Person(parent);
            retval.setString(document.getString());
        } else if (document instanceof PlaceDocumentMongo) {
            retval = new Place(parent);
            retval.setString(document.getString());
        } else if (document instanceof SourceDocumentMongo) {
            retval = new Source(parent);
            retval.setString(document.getString());
        } else if (document instanceof SourceLinkDocumentMongo) {
            final SourceLink slink = new SourceLink(parent);
            slink.setString("Source");
            slink.setToString(document.getString());
            retval = slink;
        } else if (document instanceof SubmittorDocumentMongo) {
            retval = new Submittor(parent);
            retval.setString(document.getString());
        } else if (document instanceof SubmittorLinkDocumentMongo) {
            retval = new SubmittorLink(parent);
            retval.setString(document.getString());
        } else if (document instanceof TrailerDocumentMongo) {
            retval = new Trailer(parent);
            retval.setString(document.getString());
        } else if (document instanceof WifeDocumentMongo) {
            final WifeDocumentMongo wifeDocument = (WifeDocumentMongo) document;
            final Wife wife = new Wife(parent);
            wife.setString("Wife");
            wife.setFromString(parent.getString());
            wife.setToString(wifeDocument.getString());
            retval = wife;
        } else if (document instanceof RootDocumentMongo) {
            final RootDocumentMongo rootDocument = (RootDocumentMongo) document;
            final Root root = new Root(null, "Root");
            root.setFilename(rootDocument.getFilename());
            root.setDbName(rootDocument.getDbName());
            retval = root;
        } else if (document == null) {
            throw new PersistenceException("whoops: null document");
        } else {
            throw new PersistenceException(
                    "whoops: " + document.getClass().getSimpleName());
        }
        for (final GedDocument<?> subDocument : document.getAttributes()) {
            final GedObject subGed = GedDocumentMongoFactory.getInstance()
                    .createGedObject(retval, subDocument);
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
        final Root root = new Root(null, "Root", finder);
        root.setFilename(document.getFilename());
        root.setDbName(document.getDbName());
        return root;
    }
}
