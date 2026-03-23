package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.ReadOperations;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.RequiredArgsConstructor;

/**
 * Contains tests for read operations.
 */
final class ReadOperationsTest {

    @Test
    @SuppressWarnings({ "PMD.UnitTestContainsTooManyAsserts", "unchecked" })
    void testReadFiltersNullsDeduplicatesAndSorts() {
        final FindableDocument<Root, RootDocument> repository = mock(FindableDocument.class);
        final GedObjectFileLoader loader = mock(GedObjectFileLoader.class);
        final ReadOperationsStub readOperations = new ReadOperationsStub(repository, loader);

        final RootDocument root = mock(RootDocument.class);
        final RootDocument nullStringDoc = mock(RootDocument.class);
        final RootDocument bDoc = mock(RootDocument.class);
        final RootDocument aFirst = mock(RootDocument.class);
        final RootDocument aSecond = mock(RootDocument.class);

        when(nullStringDoc.getString()).thenReturn(null);
        when(bDoc.getString()).thenReturn("B");
        when(aFirst.getString()).thenReturn("A");
        when(aSecond.getString()).thenReturn("A");

        when(repository.findAll(root)).thenReturn(Arrays.asList(
            null,
            nullStringDoc,
            bDoc,
            aFirst,
            aSecond));

        final List<RootDocument> result = readOperations.read(root);

        assertEquals(2, result.size());
        assertSame(aFirst, result.get(0));
        assertSame(bDoc, result.get(1));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testReadRootThrowsWhenDatasetMissing() {
        final FindableDocument<Root, RootDocument> repository = mock(FindableDocument.class);
        final GedObjectFileLoader loader = mock(GedObjectFileLoader.class);
        final RepositoryManagerMongo repositoryManager = mock(RepositoryManagerMongo.class);
        final ReadOperationsStub readOperations = new ReadOperationsStub(repository, loader);

        when(loader.loadDocument(repositoryManager, "missing-db")).thenReturn(null);

        assertThrows(DataSetNotFoundException.class,
            () -> readOperations.readRoot(repositoryManager, "missing-db"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testReadThrowsWhenObjectMissing() {
        final FindableDocument<Root, RootDocument> repository = mock(FindableDocument.class);
        final GedObjectFileLoader loader = mock(GedObjectFileLoader.class);
        final RepositoryManagerMongo repositoryManager = mock(RepositoryManagerMongo.class);
        final RootDocument root = mock(RootDocument.class);
        final ReadOperationsStub readOperations = new ReadOperationsStub(repository, loader);

        when(loader.loadDocument(repositoryManager, "db")).thenReturn(root);
        when(repository.findByRootAndString(root, "I999")).thenReturn(null);

        assertThrows(ObjectNotFoundException.class,
            () -> readOperations.read(repositoryManager, "db", "I999"));
    }

    /**
     * Test implementation of ReadOperations for testing the default read method.
     * The read method is the only method that has a default implementation, so we
     * can just implement the interface.
     */
    @RequiredArgsConstructor
    private static final class ReadOperationsStub
        implements ReadOperations<Root, RootDocument, ApiObject> {
        /** */
        private final FindableDocument<Root, RootDocument> repository;
        /** */
        private final GedObjectFileLoader loader;

        /**
         * Gets the repository.
         *
         * @return the repository
         */
        @Override
        public FindableDocument<Root, RootDocument> getRepository() {
            return repository;
        }

        /**
         * Gets the loader.
         *
         * @return the loader
         */
        @Override
        public GedObjectFileLoader getLoader() {
            return loader;
        }

        /**
         * Gets the ged class.
         *
         * @return the ged class
         */
        @Override
        public Class<Root> getGedClass() {
            return Root.class;
        }

        /**
         * Returns the list.
         *
         * @param db the db
         * @return the resulting list
         */
        @Override
        public List<ApiObject> readAll(final String db) {
            return List.of();
        }

        /**
         * Returns the api object.
         *
         * @param db the db
         * @param id the unique identifier for the target
         * @return the resulting api object
         */
        @Override
        public ApiObject readOne(final String db, final String id) {
            return null;
        }

        /**
         * Returns the root document.
         *
         * @param repositoryManager the repository manager
         * @param dbName the db name to use
         * @param idString the id string
         * @return the resulting root document
         */
        @Override
        public RootDocument read(final RepositoryManagerMongo repositoryManager,
                final String dbName,
                final String idString) {
            return ReadOperations.super.read(repositoryManager, dbName, idString);
        }
    }
}
