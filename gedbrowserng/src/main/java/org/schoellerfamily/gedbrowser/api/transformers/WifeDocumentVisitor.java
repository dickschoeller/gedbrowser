package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.WifeDocument;

/**
 * The visitor for WifeDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface WifeDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final WifeDocument document) {
        setBaseObject(createAttribute(document));
    }
}
