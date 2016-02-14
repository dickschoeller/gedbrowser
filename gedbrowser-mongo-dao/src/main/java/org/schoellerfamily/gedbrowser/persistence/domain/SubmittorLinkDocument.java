package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.SubmittorLink;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;

/**
 * @author Dick Schoeller
 */
public class SubmittorLinkDocument extends GedDocument<SubmittorLink> {
    /**
     * Constructor.
     */
    public SubmittorLinkDocument() {
        setType("submittorlink");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof SubmittorLink)) {
            throw new PersistenceException("Wrong type");
        }
        final SubmittorLink gedObject = (SubmittorLink) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
