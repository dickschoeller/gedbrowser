package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.PlaceDocument;

/**
 * The visitor for PlaceDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface PlaceDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final PlaceDocument document) {
        setBaseObject(createAttribute(document));
    }
}
