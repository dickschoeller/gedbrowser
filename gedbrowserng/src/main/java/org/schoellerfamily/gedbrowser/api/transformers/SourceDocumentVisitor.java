package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;

/**
 * The visitor for SourceDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface SourceDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SourceDocument document) {
        // Use builder so we can set the title field explicitly
        setBaseObject(ApiSource.builder()
            .type(document.getType())
            .string(document.getString())
            .title(title(document))
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * @param document the document whose title we want
     * @return the title
     */
    private String title(final SourceDocument document) {
        for (final GedDocument<?> g : document.getAttributes()) {
            if ("Title".equals(g.getString())) {
                final AttributeDocument a = (AttributeDocument) g;
                return a.getTail();
            }
        }
        return "Unknown";
    }
}
