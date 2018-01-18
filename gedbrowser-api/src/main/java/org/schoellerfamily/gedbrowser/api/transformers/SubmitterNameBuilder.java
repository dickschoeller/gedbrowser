package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;

/**
 * @author Dick Schoeller
 */
public interface SubmitterNameBuilder {
    /**
     * @param document the document whose name we want
     * @return the name
     */
    default String name(final SubmitterDocument document) {
        for (final GedDocument<?> g : document.getAttributes()) {
            if ("name".equals(g.getType())) {
                return g.getString().replace("/", " ").trim();
            }
        }
        return "? ?";
    }
}
