package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;

/**
 * @author Dick Schoeller
 *
 * @param <Y> the type to convert from
 * @param <Z> the type to convert to
 */
public interface Converter<Y extends GedDocument<?>, Z extends ApiObject> {
    /**
     * @return the class the converts from DB model to API model
     */
    DocumentToApiModelTransformer getD2dm();

    /**
     * @param document the input document
     * @return the output API document
     */
    default Z convert(final Y document) {
        return getD2dm().convert(document);
    }

    /**
     * @param documents the list of input documents
     * @return the output list of API documents
     */
    default List<Z> convert(
            final List<Y> documents) {
        return getD2dm().convert(documents);
    }
}
