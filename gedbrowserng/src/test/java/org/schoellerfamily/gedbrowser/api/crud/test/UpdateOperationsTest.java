package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.crud.UpdateOperations;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.RequiredArgsConstructor;

/**
 * Contains tests for update operations.
 */
final class UpdateOperationsTest {

    @Test
    @SuppressWarnings("unchecked")
    void testUpdateReturnsNullWhenRepositoryThrows() {
        final FindableDocument<Root, RootDocument> repository = mock(FindableDocument.class);
        final GedObjectToGedDocumentMongoConverter converter =
            mock(GedObjectToGedDocumentMongoConverter.class);
        final DocumentToApiModelTransformer transformer = mock(DocumentToApiModelTransformer.class);
        final UpdateOperationsStub updateOperations =
            new UpdateOperationsStub(repository, converter, transformer);

        final Root object = mock(Root.class);

        when(object.getFilename()).thenReturn("db");
        when(object.getString()).thenReturn("I1");
        when(repository.findByFileAndString("db", "I1"))
            .thenThrow(new RuntimeException("boom"));

        assertNull(updateOperations.update(object));
    }

    /**
     * Test stub for UpdateOperations default methods.
     */
    @RequiredArgsConstructor
    private static final class UpdateOperationsStub
        implements UpdateOperations<Root, RootDocument, ApiObject> {
        /** */
        private final FindableDocument<Root, RootDocument> repository;
        /** */
        private final GedObjectToGedDocumentMongoConverter converter;
        /** */
        private final DocumentToApiModelTransformer transformer;

        @Override
        public FindableDocument<Root, RootDocument> getRepository() {
            return repository;
        }

        @Override
        public GedObjectToGedDocumentMongoConverter getConverter() {
            return converter;
        }

        @Override
        public DocumentToApiModelTransformer getD2dm() {
            return transformer;
        }

        @Override
        public ApiObject updateOne(final String db, final String id, final ApiObject object) {
            return null;
        }
    }
}
