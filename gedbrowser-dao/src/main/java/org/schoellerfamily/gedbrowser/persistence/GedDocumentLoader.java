package org.schoellerfamily.gedbrowser.persistence;

import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;

/**
 * @author Dick Schoeller
 */
public interface GedDocumentLoader {
    /**
     * @param document the document
     * @param gedAttributes the attributes to add
     */
    void loadAttributes(GedDocument<?> document,
            List<GedObject> gedAttributes);
}
