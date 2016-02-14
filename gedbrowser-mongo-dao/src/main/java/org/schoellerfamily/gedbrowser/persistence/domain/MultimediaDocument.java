package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class MultimediaDocument extends GedDocument<Multimedia> {
    /** */
    private String tail;

    /**
     * Constructor.
     */
    public MultimediaDocument() {
        this.setType("multimedia");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Multimedia)) {
            throw new PersistenceException("Wrong type");
        }
        final Multimedia gedObject = (Multimedia) ged;
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
