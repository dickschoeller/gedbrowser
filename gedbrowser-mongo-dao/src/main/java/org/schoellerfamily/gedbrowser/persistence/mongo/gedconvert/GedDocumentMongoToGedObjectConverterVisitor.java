package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Child;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.FamC;
import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Husband;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.datamodel.Wife;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.ChildDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamCDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.FamSDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.HusbandDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SourceLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.SubmittorLinkDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.WifeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public final class GedDocumentMongoToGedObjectConverterVisitor
        extends TopLevelGedDocumentMongoToGedObjectVisitor
        implements GedDocumentMongoVisitor {
    /**
     * @param parent the parent of the object we are going to create
     */
    public GedDocumentMongoToGedObjectConverterVisitor(final GedObject parent) {
        super(parent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final AttributeDocumentMongo document) {
        final Attribute attribute = new Attribute(getParent());
        attribute.setString(document.getString());
        attribute.setTail(document.getTail());
        setGedObject(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final ChildDocumentMongo document) {
        final Child child = new Child(getParent(), "Child",
                new ObjectId(document.getString()));
        child.setFromString(getParent().getString());
        setGedObject(child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final DateDocumentMongo document) {
        setGedObject(new Date(getParent(), document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final MultimediaDocumentMongo document) {
        final Multimedia attribute = new Multimedia(getParent(),
                document.getString());
        attribute.setTail(document.getTail());
        setGedObject(attribute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final NameDocumentMongo document) {
        setGedObject(new Name(getParent(), document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamCDocumentMongo document) {
        final FamC famc = new FamC(getParent(), "Child of Family",
                new ObjectId(document.getString()));
        famc.setFromString(getParent().getString());
        setGedObject(famc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final FamSDocumentMongo document) {
        final FamS fams = new FamS(getParent(), "Spouse of Family",
                new ObjectId(document.getString()));
        fams.setFromString(getParent().getString());
        setGedObject(fams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final HusbandDocumentMongo document) {
        final Husband husband = new Husband(getParent(), "Husband",
                new ObjectId(document.getString()));
        husband.setFromString(getParent().getString());
        setGedObject(husband);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final PlaceDocumentMongo document) {
        setGedObject(new Place(getParent(), document.getString()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SourceLinkDocumentMongo document) {
        setGedObject(new SourceLink(getParent(), "Source",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final SubmittorLinkDocumentMongo document) {
        setGedObject(new SubmittorLink(getParent(), "Submittor",
                new ObjectId(document.getString())));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final WifeDocumentMongo document) {
        final WifeDocumentMongo wifeDocument = (WifeDocumentMongo) document;
        final Wife wife = new Wife(getParent(), "Wife",
                new ObjectId(wifeDocument.getString()));
        wife.setFromString(getParent().getString());
        setGedObject(wife);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void visit(final RootDocumentMongo document) {
        final Root root = new Root("Root");
        root.setFilename(document.getFilename());
        root.setDbName(document.getDbName());
        setGedObject(root);
    }
}
