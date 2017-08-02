package org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.Date;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.datamodel.Name;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.AttributeDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.DateDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.MultimediaDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.NameDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PlaceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public final class GedDocumentMongoToGedObjectConverterVisitor
        extends GedLinkDocumentMongoToGedObjectConverterVisitor
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
    public void visit(final PlaceDocumentMongo document) {
        setGedObject(new Place(getParent(), document.getString()));
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
