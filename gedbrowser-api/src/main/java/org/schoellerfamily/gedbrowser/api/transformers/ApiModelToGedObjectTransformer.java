package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.PersonDocumentMongo;

/**
 * @author Dick Schoeller
 */
public class ApiModelToGedObjectTransformer {
    /**
     * @param dbName the name of the data set
     * @param person the person object in
     * @return the person document out
     */
    public GedDocument<?> convert(final String dbName, final ApiObject person) {
        final PersonDocument personDocument = new PersonDocumentMongo();
        personDocument.setDbName(dbName);
        personDocument.setString(person.getString());
        return personDocument;
    }
}
