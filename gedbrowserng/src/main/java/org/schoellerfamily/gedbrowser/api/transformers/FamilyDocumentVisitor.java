package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;

/**
 * The visitor for FamilyDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface FamilyDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final FamilyDocument document) {
        setBaseObject(ApiFamily.builder()
            .type(document.getType())
            .string(document.getString())
            .attributes(processAttributes(document))
            .build());
    }
}
