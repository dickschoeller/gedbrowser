package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.FamCDocument;

/**
 * The visitor for FamCDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface FamCDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final FamCDocument document) {
        setBaseObject(createAttribute(document));
    }
}
