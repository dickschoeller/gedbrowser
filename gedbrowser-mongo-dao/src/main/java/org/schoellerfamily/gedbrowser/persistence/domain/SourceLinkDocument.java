package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.SourceLink;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class SourceLinkDocument extends GedDocument<SourceLink> {
    /**
     * Constructor.
     */
    public SourceLinkDocument() {
        setType("sourcelink");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof SourceLink)) {
            throw new PersistenceException("Wrong type");
        }
        final SourceLink gedObject = (SourceLink) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
