package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Multimedia;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;

/**
 * @author Dick Schoeller
 */
public class MultimediaDocumentMongo extends GedDocumentMongo<Multimedia>
        implements MultimediaDocument {
    /** */
    private String tail;

    /**
     * Constructor.
     */
    public MultimediaDocumentMongo() {
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

    @Override
    public final String getTail() {
        return this.tail;
    }

    @Override
    public final void setTail(final String tail) {
        this.tail = tail;
    }
}
