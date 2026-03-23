package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;

/**
 * The visitor for AttributeDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface AttributeDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final AttributeDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .tail(document.getTail())
                .attributes(processAttributes(document))
                .build());
    }
}
