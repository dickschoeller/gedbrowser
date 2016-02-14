package org.schoellerfamily.gedbrowser.persistence.domain;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.PersistenceException;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Dick Schoeller
 */
@Document(collection = "heads")
@CompoundIndexes({
    @CompoundIndex(name = "unique_idx", def = "{'string': 1, 'filename': 1}")
})
public class HeadDocument extends GedDocument<Head> {
    /**
     * Constructor.
     */
    public HeadDocument() {
        setType("head");
    }

    @Override
    public final void loadGedObject(final GedObject ged) {
        if (!(ged instanceof Head)) {
            throw new PersistenceException("Wrong type");
        }
        final Head gedObject = (Head) ged;
        this.setGedObject(gedObject);
        this.setString(gedObject.getString());
        this.setFilename(gedObject.getFilename());
        this.loadAttributes(gedObject.getAttributes());
    }
}
