package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.HusbandDocument;

/**
 * The visitor for HusbandDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface HusbandDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final HusbandDocument document) {
        setBaseObject(createAttribute(document));
    }
}
