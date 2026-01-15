package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * @author Dick Schoeller
 */
public final class AttributeDocumentMongo extends GedDocumentMongo<Attribute>
        implements AttributeDocument {
    /** */
    private String tail;

    @Override
    public String getType() {
        return "attribute";
    }

    @Override
    public void loadGedObject(final GedDocumentLoader loader,
            final GedObject ged) {
        if (!(ged instanceof Attribute)) {
            throw new PersistenceException("Wrong type");
        }
        final Attribute gedObject = (Attribute) ged;
        this.setTail(gedObject.getTail());
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        loader.loadAttributes(this, gedObject.getAttributes());
    }

    @Override
    public void setTail(final String tail) {
        this.tail = tail;
    }

    @Override
    public String getTail() {
        return this.tail;
    }

    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
