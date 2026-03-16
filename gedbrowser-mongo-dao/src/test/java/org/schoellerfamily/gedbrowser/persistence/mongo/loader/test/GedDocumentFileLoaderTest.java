package org.schoellerfamily.gedbrowser.persistence.mongo.loader.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;

/**
 * Tests {@link GedDocumentFileLoader} behavior when validating database names.
 * <p>
 * These tests deliberately mock {@link java.nio.file.Paths# get(String, String...)}
 * and related {@link java.nio.file.Path} methods so that the internal
 * {@code validateDatabaseName} logic can be driven through edge-case branches
 * (such as when the normalized path has a parent, when the file name is
 * {@code null}, or when the file name does not match the database name)
 * without relying on the real filesystem. This explains the use of static
 * filesystem API mocking in this class.
 */
public class GedDocumentFileLoaderTest {

    private GedDocumentFileLoader createLoader() {
        final FinderStrategy finder = Mockito.mock(FinderStrategy.class);
        final GedLineToGedObjectTransformer g2g = Mockito.mock(GedLineToGedObjectTransformer.class);
        final GedObjectToGedDocumentMongoConverter conv = Mockito
                .mock(GedObjectToGedDocumentMongoConverter.class);
        final RootDocumentRepositoryMongo repo = Mockito.mock(RootDocumentRepositoryMongo.class);
        return new GedDocumentFileLoader(finder, g2g, conv, repo, "/tmp");
    }

    @Test
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testNormalizedPathHasParentThrows() {
        final GedDocumentFileLoader loader = createLoader();
        final String dbName = "validname";

        final Path mockNormalized = Mockito.mock(Path.class);
        final Path mockParent = Mockito.mock(Path.class);

        try (MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            // When validateDatabaseName calls Paths.get(dbName).normalize() return our mock
            pathsMock.when(() -> Paths.get(dbName)).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.normalize()).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.getParent()).thenReturn(mockParent);

            final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> loader.buildFileName(dbName));
            assertTrue(ex.getMessage().contains("Database name contains path components"));
        }
    }

    @Test
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testFileNamePathNullThrows() {
        final GedDocumentFileLoader loader = createLoader();
        final String dbName = "validname";

        final Path mockNormalized = Mockito.mock(Path.class);

        try (MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            pathsMock.when(() -> Paths.get(dbName)).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.normalize()).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.getParent()).thenReturn(null);
            Mockito.when(mockNormalized.getFileName()).thenReturn(null);

            final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> loader.buildFileName(dbName));
            assertTrue(ex.getMessage().contains("Database name does not contain a valid filename"));
        }
    }

    @Test
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testFileNameMismatchThrows() {
        final GedDocumentFileLoader loader = createLoader();
        final String dbName = "validname";

        final Path mockNormalized = Mockito.mock(Path.class);
        final Path mockFileNamePath = Mockito.mock(Path.class);

        try (MockedStatic<Paths> pathsMock = Mockito.mockStatic(Paths.class)) {
            pathsMock.when(() -> Paths.get(dbName)).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.normalize()).thenReturn(mockNormalized);
            Mockito.when(mockNormalized.getParent()).thenReturn(null);
            Mockito.when(mockNormalized.getFileName()).thenReturn(mockFileNamePath);
            Mockito.when(mockFileNamePath.toString()).thenReturn("differentname");

            final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> loader.buildFileName(dbName));
            assertTrue(ex.getMessage().contains("Database name contains path components"));
        }
    }
}
