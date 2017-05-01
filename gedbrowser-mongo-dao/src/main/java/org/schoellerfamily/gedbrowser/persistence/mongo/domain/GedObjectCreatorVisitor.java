package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

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
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
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
@SuppressWarnings("PMD.TooManyMethods")
public final class GedObjectCreatorVisitor implements GedDocumentMongoVisitor {
    /**
     * The parent of the object we are going to create.
     */
    private final GedObject parent;

    /**
     * The created object.
     */
    private GedObject gedObject;

    /**
     * @param parent the parent of the object we are going to create
     */
    public GedObjectCreatorVisitor(final GedObject parent) {
        this.parent = parent;
    }

    /**
     * @return the created object
     */
    public GedObject getGedObject() {
        return gedObject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PersonDocumentMongo document) {
        gedObject = new Person(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamilyDocumentMongo document) {
        gedObject = new Family(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceDocumentMongo document) {
        gedObject = new Source(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HeadDocumentMongo document) {
        gedObject = new Head(parent, "Header");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorDocumentMongo document) {
        gedObject = new Submittor(parent, new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final TrailerDocumentMongo document) {
        gedObject = new Trailer(parent, document.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final GedDocumentMongo<? extends GedObject> document) {
        throw new PersistenceException(
                "whoops: " + document.getClass().getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final AttributeDocumentMongo document) {
        final Attribute attribute = new Attribute(parent);
        attribute.setString(document.getString());
        attribute.setTail(document.getTail());
        gedObject = attribute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ChildDocumentMongo document) {
        final Child child = new Child(parent, "Child",
                new ObjectId(document.getString()));
        child.setFromString(parent.getString());
        gedObject = child;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final DateDocumentMongo document) {
        gedObject = new Date(parent, document.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultimediaDocumentMongo document) {
        final Multimedia attribute = new Multimedia(parent,
                document.getString());
        attribute.setTail(document.getTail());
        gedObject = attribute;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NameDocumentMongo document) {
        gedObject = new Name(parent, document.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamCDocumentMongo document) {
        final FamC famc = new FamC(parent, "Child of Family",
                new ObjectId(document.getString()));
        famc.setFromString(parent.getString());
        gedObject = famc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamSDocumentMongo document) {
        final FamS fams = new FamS(parent, "Spouse of Family",
                new ObjectId(document.getString()));
        fams.setFromString(parent.getString());
        gedObject = fams;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HusbandDocumentMongo document) {
        final Husband husband = new Husband(parent, "Husband",
                new ObjectId(document.getString()));
        husband.setFromString(parent.getString());
        gedObject = husband;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PlaceDocumentMongo document) {
        gedObject = new Place(parent, document.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLinkDocumentMongo document) {
        gedObject = new SourceLink(parent, "Source",
                new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLinkDocumentMongo document) {
        gedObject = new SubmittorLink(parent, "Submittor",
                new ObjectId(document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final WifeDocumentMongo document) {
        final WifeDocumentMongo wifeDocument = (WifeDocumentMongo) document;
        final Wife wife = new Wife(parent, "Wife",
                new ObjectId(wifeDocument.getString()));
        wife.setFromString(parent.getString());
        gedObject = wife;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final RootDocumentMongo document) {
        final Root root = new Root("Root");
        root.setFilename(document.getFilename());
        root.setDbName(document.getDbName());
        gedObject = root;
    }
}
