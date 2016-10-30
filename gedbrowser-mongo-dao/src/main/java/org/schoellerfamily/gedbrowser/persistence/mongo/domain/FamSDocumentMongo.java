package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.FamS;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.FamSDocument;

/**
 * @author Dick Schoeller
 */
public class FamSDocumentMongo extends GedDocumentMongo<FamS>
        implements FamSDocument {
    /**
     * Constructor.
     */
    public FamSDocumentMongo() {
        setType("fams");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof FamS)) {
            throw new PersistenceException("Wrong type");
        }
        final FamS gedObject = (FamS) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getToString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
