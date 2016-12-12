package org.schoellerfamily.gedbrowser.persistence.mongo.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "submittors")
@CompoundIndexes({
    @CompoundIndex(name = "unique_idx", def = "{'string': 1, 'filename': 1}")
})
public class SubmittorDocumentMongo extends GedDocumentMongo<Submittor>
        implements SubmittorDocument {
    /**
     * Constructor.
     */
    public SubmittorDocumentMongo() {
        setType("submittor");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Submittor)) {
            throw new PersistenceException("Wrong type");
        }
        final Submittor gedObject = (Submittor) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
