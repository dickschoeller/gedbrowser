package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.Attribute;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class AttributeDocument extends GedDocument<Attribute> {
    /** */
    private String tail;

    /**
     * Constructor.
     */
    public AttributeDocument() {
        this.setType("attribute");
    }

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
     * @return the tail string
     */
    public final String getTail() {
        return this.tail;
    }

    /**
     * @param tail the new tail string
     */
    public final void setTail(final String tail) {
        this.tail = tail;
    }
}
