package org.schoellerfamily.gedbrowser.persistence.domain; // NOPMD

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

/**
 * @author Dick Schoeller
 */
public final class GedDocumentFactory { // NOPMD
    /** */
    private static final GedDocumentFactory INSTANCE =
            new GedDocumentFactory();

    /**
     * Holds the mapping between GedObject and GedDocument.
     */
    private static final Map<Class<? extends GedObject>,
        Class<? extends GedDocument<? extends GedObject>>> CLASS_MAP =
            new HashMap<>();
    static {
        CLASS_MAP.put(Attribute.class, AttributeDocument.class);
        CLASS_MAP.put(Child.class, ChildDocument.class);
        CLASS_MAP.put(Date.class, DateDocument.class);
        CLASS_MAP.put(Name.class, NameDocument.class);
        CLASS_MAP.put(Family.class, FamilyDocument.class);
        CLASS_MAP.put(FamC.class, FamCDocument.class);
        CLASS_MAP.put(FamS.class, FamSDocument.class);
        CLASS_MAP.put(Head.class, HeadDocument.class);
        CLASS_MAP.put(Husband.class, HusbandDocument.class);
        CLASS_MAP.put(Multimedia.class, MultimediaDocument.class);
        CLASS_MAP.put(Person.class, PersonDocument.class);
        CLASS_MAP.put(Place.class, PlaceDocument.class);
        CLASS_MAP.put(Root.class, RootDocument.class);
        CLASS_MAP.put(Source.class, SourceDocument.class);
        CLASS_MAP.put(SourceLink.class, SourceLinkDocument.class);
        CLASS_MAP.put(Submittor.class, SubmittorDocument.class);
        CLASS_MAP.put(SubmittorLink.class, SubmittorLinkDocument.class);
        CLASS_MAP.put(Trailer.class, TrailerDocument.class);
        CLASS_MAP.put(Wife.class, WifeDocument.class);
    }

    /**
     * Constructor.
     */
    private GedDocumentFactory() {
    }

    /**
     * @return the singleton
     */
    public static GedDocumentFactory getInstance() {
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
        final Class<? extends GedDocument<?>> mongoClass =
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
        if (document instanceof AttributeDocument) {
            final AttributeDocument attributeDocument =
                    (AttributeDocument) document;
            final Attribute attribute = new Attribute(parent);
            attribute.setString(attributeDocument.getString());
            attribute.setTail(attributeDocument.getTail());
            retval = attribute;
        } else if (document instanceof ChildDocument) {
            final ChildDocument childDocument =
                    (ChildDocument) document;
            final Child child = new Child(parent);
            child.setString("Child");
            child.setFromString(parent.getString());
            child.setToString(childDocument.getString());
            retval = child;
        } else if (document instanceof DateDocument) {
            retval = new Date(parent, document.getString());
        } else if (document instanceof MultimediaDocument) {
            final MultimediaDocument multimediaDocument =
                    (MultimediaDocument) document;
            final Multimedia attribute = new Multimedia(parent);
            attribute.setString(multimediaDocument.getString());
            attribute.setTail(multimediaDocument.getTail());
            retval = attribute;
        } else if (document instanceof NameDocument) {
            retval = new Name(parent, document.getString());
        } else if (document instanceof FamilyDocument) {
            retval = new Family(parent);
            retval.setString(document.getString());
        } else if (document instanceof FamCDocument) {
            final FamCDocument famcDocument = (FamCDocument) document;
            final FamC famc = new FamC(parent);
            famc.setFromString(parent.getString());
            famc.setToString(famcDocument.getString());
            famc.setString("Child of Family");
            retval = famc;
        } else if (document instanceof FamSDocument) {
            final FamSDocument famsDocument = (FamSDocument) document;
            final FamS fams = new FamS(parent);
            fams.setFromString(parent.getString());
            fams.setToString(famsDocument.getString());
            fams.setString("Spouse of Family");
            retval = fams;
        } else if (document instanceof HeadDocument) {
            retval = new Head(parent);
            retval.setString("Header");
        } else if (document instanceof HusbandDocument) {
            final HusbandDocument husbandDocument =
                    (HusbandDocument) document;
            final Husband husband = new Husband(parent);
            husband.setString("Husband");
            husband.setFromString(parent.getString());
            husband.setToString(husbandDocument.getString());
            retval = husband;
        } else if (document instanceof PersonDocument) {
            retval = new Person(parent);
            retval.setString(document.getString());
        } else if (document instanceof PlaceDocument) {
            retval = new Place(parent);
            retval.setString(document.getString());
        } else if (document instanceof SourceDocument) {
            retval = new Source(parent);
            retval.setString(document.getString());
        } else if (document instanceof SourceLinkDocument) {
            final SourceLink slink = new SourceLink(parent);
            slink.setString("Source");
            slink.setToString(document.getString());
            retval = slink;
        } else if (document instanceof SubmittorDocument) {
            retval = new Submittor(parent);
            retval.setString(document.getString());
        } else if (document instanceof SubmittorLinkDocument) {
            retval = new SubmittorLink(parent);
            retval.setString(document.getString());
        } else if (document instanceof TrailerDocument) {
            retval = new Trailer(parent);
            retval.setString(document.getString());
        } else if (document instanceof WifeDocument) {
            final WifeDocument wifeDocument = (WifeDocument) document;
            final Wife wife = new Wife(parent);
            wife.setString("Wife");
            wife.setFromString(parent.getString());
            wife.setToString(wifeDocument.getString());
            retval = wife;
        } else if (document instanceof RootDocument) {
            final RootDocument rootDocument = (RootDocument) document;
            final Root root = new Root(null, "Root");
            root.setFilename(rootDocument.getFilename());
            root.setDbName(rootDocument.getDbName());
            retval = root;
        } else {
            throw new PersistenceException("whoops");
        }
        for (final GedDocument<?> subDocument : document.getAttributes()) {
            final GedObject subGed = GedDocumentFactory.getInstance()
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
    public Root createRoot(
            final RootDocument document, final FinderStrategy finder) {
        final RootDocument rootDocument = (RootDocument) document;
        final Root root = new Root(null, "Root", finder);
        root.setFilename(rootDocument.getFilename());
        root.setDbName(rootDocument.getDbName());
        return root;
    }
}
