package org.schoellerfamily.gedbrowser.api.transformers;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;

/**
 * @author Dick Schoeller
 */
public class DocumentToApiModelTransformer {
    /**
     * Constructor.
     */
    public DocumentToApiModelTransformer() {
        super();
    }

    /**
     * @param document the document to convert
     * @return the resulting object
     */
    public final ApiHead convert(final HeadDocument document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (ApiHead) v.getBaseObject();
    }

    /**
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param listIn list of FamilyDocument
     * @return list of ApiFamily
     */
    public final <T extends ApiObject, V extends GedDocument<?>> List<T>
            convert(final List<V> listIn) {
        final List<T> listOut = new ArrayList<>();
        for (final V family : listIn) {
            listOut.add(convert(family));
        }
        listOut.sort(new GetStringComparator());
        return listOut;
    }

    /**
     * @param <T> the data type returned
     * @param <V> the data type input
     * @param document the document to convert
     * @return the resulting object
     */
    @SuppressWarnings("unchecked")
    public final <T extends ApiObject, V extends GedDocument<?>> T convert(
            final V document) {
        final DocumentToApiModelVisitor v =
                new DocumentToApiModelVisitor();
        document.accept(v);
        return (T) v.getBaseObject();
    }
}
