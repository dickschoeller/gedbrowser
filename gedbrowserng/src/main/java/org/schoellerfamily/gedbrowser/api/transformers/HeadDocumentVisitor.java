package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;

/**
 * The visitor for HeadDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface HeadDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final HeadDocument document) {
        setBaseObject(ApiHead.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }
}
