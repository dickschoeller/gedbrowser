package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;

/**
 * The visitor for SubmitterDocument.
 *
 * @author Richard Schoeller
 */
/* default */ interface SubmitterDocumentVisitor extends GedDocumentBaseVisitor {
    @Override
    default void visit(final SubmitterDocument document) {
        setBaseObject(ApiSubmitter.builder()
            .type(document.getType())
            .string(document.getString())
            .name(name(document))
            .attributes(processAttributes(document))
            .build());
    }

    /**
     * @param document the document whose name we want
     * @return the name
     */
    private String name(final SubmitterDocument document) {
        for (final GedDocument<?> g : document.getAttributes()) {
            if ("name".equals(g.getType())) {
                return g.getString().replace("/", " ").trim();
            }
        }
        return "? ?";
    }
}
