package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.api.crud.ReadOperations;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * Contains tests for read operations.
 */
final class ReadOperationsTest {

    @Test
    void testReadFiltersNullsDeduplicatesAndSorts() {
        final FindableDocument<Root, RootDocument> repository = Mockito.mock(FindableDocument.class);
        final GedObjectFileLoader loader = Mockito.mock(GedObjectFileLoader.class);
        final TestReadOperations readOperations = new TestReadOperations(repository, loader);

        final RootDocument root = Mockito.mock(RootDocument.class);
        final RootDocument nullStringDoc = Mockito.mock(RootDocument.class);
        final RootDocument bDoc = Mockito.mock(RootDocument.class);
        final RootDocument aFirst = Mockito.mock(RootDocument.class);
        final RootDocument aSecond = Mockito.mock(RootDocument.class);

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

    private static final class TestReadOperations
            implements ReadOperations<Root, RootDocument, ApiObject> {
        private final FindableDocument<Root, RootDocument> repository;
        private final GedObjectFileLoader loader;

        TestReadOperations(final FindableDocument<Root, RootDocument> repository,
                final GedObjectFileLoader loader) {
            this.repository = repository;
            this.loader = loader;
        }

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
