package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.persistence.domain.MultimediaDocument;

/**
 * The visitor for MultimediaDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface MultimediaDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final MultimediaDocument document) {
        setBaseObject(
            ApiAttribute.builder()
                .type(document.getType())
                .string(document.getString())
                .tail(document.getTail())
                .attributes(processAttributes(document))
                .build());
    }
}
