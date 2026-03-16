package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.GedDocumentLoader;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;

/**
 * Represents attribute document mongo for persistence operations.
 *
 * @author Richard Schoeller
 */
public final class AttributeDocumentMongo extends GedDocumentMongo<Attribute>
        implements AttributeDocument {
    /**
     * Creates a new AttributeDocumentMongo.
     */
    public AttributeDocumentMongo() {
    }

    /**
     * The tail value.
     */
    private String tail;

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return "attribute";
    }

    /**
     * Loads the ged object.
     *
     * @param loader the loader
     */
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

    /**
     * Sets the tail.
     *
     * @param tail the tail
     */
    @Override
    public void setTail(final String tail) {
        this.tail = tail;
    }

    /**
     * Gets the tail.
     *
     * @return the tail
     */
    @Override
    public String getTail() {
        return this.tail;
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final TopLevelGedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedDocumentMongoVisitor visitor) {
        visitor.visit(this);
    }
}
