package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;

/**
 * The visitor for TrailerDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface TrailerDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final TrailerDocument document) {
        setBaseObject(ApiObject.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }
}
