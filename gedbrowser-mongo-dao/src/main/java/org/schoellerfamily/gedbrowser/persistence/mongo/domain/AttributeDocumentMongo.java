package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;

/**
 * @author Dick Schoeller
 */
public class AttributeDocumentMongo extends GedDocumentMongo<Attribute>
        implements AttributeDocument {
    /** */
    private String tail;

    /**
     * Constructor.
     */
    public AttributeDocumentMongo() {
        this.setType("attribute");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Attribute)) {
            throw new PersistenceException("Wrong type");
        }
        final Attribute gedObject = (Attribute) ged;
        this.setTail(gedObject.getTail());
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getTail() {
        return this.tail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setTail(final String tail) {
        this.tail = tail;
    }
}
