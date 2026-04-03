package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;

import lombok.NoArgsConstructor;

/**
 * Represents document to api model transformer.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public class DocumentToApiModelTransformer {
    /**
     * Executes convert.
     *
     * @param document the document
     * @return the resulting api head
     */
    public final ApiHead convert(final HeadDocument document) {
        final DocumentToApiModelVisitor v = new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiHead) v.getBaseObject();
    }

    /**
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param listIn list of FamilyDocument
     * @return list of ApiFamily
     */
    @SuppressWarnings("unchecked")
    public final <T extends ApiObject, V extends GedDocument<?>> List<T> convert(
            final List<V> listIn) {
        return listIn.stream()
            .map(v -> {
                if (v instanceof HeadDocument h) {
                    return (T) convert(h);
                }
                return (T) convert(v);
            })
            .sorted(new GetStringComparator())
            .toList();
    }

    /**
     * Executes convert.
     *
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param document the document
     * @return the resulting t
     */
    @SuppressWarnings("unchecked")
    public final <T extends ApiObject, V extends GedDocument<?>> T convert(
            final V document) {
        final DocumentToApiModelVisitor v = new DocumentToApiModelVisitor();
        document.accept(v);
        return (T) v.getBaseObject();
    }
}
