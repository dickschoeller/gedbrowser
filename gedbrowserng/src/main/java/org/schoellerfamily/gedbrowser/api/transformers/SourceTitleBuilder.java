package org.schoellerfamily.gedbrowser.api.transformers;

import org.schoellerfamily.gedbrowser.persistence.domain.AttributeDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;

/**
 * @author Dick Schoeller
 */
public interface SourceTitleBuilder {
    /**
     * @param document the document whose title we want
     * @return the title
     */
    default String title(final SourceDocument document) {
        for (final GedDocument<?> g : document.getAttributes()) {
            if ("Title".equals(g.getString())) {
                final AttributeDocument a = (AttributeDocument) g;
                return a.getTail();
            }
        }
        return "Unknown";
    }

}
