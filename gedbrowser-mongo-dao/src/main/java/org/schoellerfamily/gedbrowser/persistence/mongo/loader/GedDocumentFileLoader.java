package org.schoellerfamily.gedbrowser.persistence.mongo.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.reader.GedFile;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Slf4j
@RequiredArgsConstructor
public class GedDocumentFileLoader {
    /**
     * Implements finding other objects.
     */
    private final FinderStrategy finder;

    /**
     * Converts AbstractGedLine hierarchy to GedObject hierarchy.
     */
    private final GedLineToGedObjectTransformer g2g;

    /**
     * Implements conversion from GedObject to GedDocumentMongo.
     */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    private final RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private final String gedbrowserHome;

    /**
     * @param repositoryManager the repository manager
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public RootDocument loadDocument(final RepositoryManagerMongo repositoryManager,
        final String dbName) {
        log.info("entering loadDocument({})", dbName);
        final String filename = buildFileName(dbName);
        final RootDocument rootDocument = rootDocumentRepository.findByFileAndString(filename,
            "Root");
        if (rootDocument == null) {
            return loadRepository(repositoryManager, dbName);
        }

        return rootDocument;
    }

    /**
     * @param dbName the name of the DB
     * @return the derived filename
     * @throws IllegalArgumentException if dbName contains path traversal sequences
     */
    protected String buildFileName(final String dbName) {
        validateDatabaseName(dbName);
        return Paths.get(gedbrowserHome, dbName + ".ged").toString();
    }

    /**
     * Validates that the database name does not contain path traversal sequences
     * or other potentially dangerous path components.
     *
     * @param dbName the database name to validate
     * @throws IllegalArgumentException if dbName is invalid
     */
    private void validateDatabaseName(final String dbName) {
        if (StringUtils.isEmpty(dbName)) {
            throw new IllegalArgumentException("Database name cannot be null or empty");
        }

        // Check for basic path traversal and separators
        if (dbName.contains("..") || dbName.contains("/") || dbName.contains("\\")) {
            throw new IllegalArgumentException(
                "Database name contains invalid characters: " + dbName);
        }

        // Check for NTFS alternate data streams (contains colon)
        if (dbName.contains(":")) {
            throw new IllegalArgumentException(
                "Database name contains invalid characters (NTFS stream): " + dbName);
        }

        // Check for Windows reserved device names
        final String upperDbName = dbName.toUpperCase(Locale.ROOT);
        final String[] reservedNames = {
            "CON", "PRN", "AUX", "NUL",
            "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
            "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"
        };

        for (final String reserved : reservedNames) {
            // Check if dbName equals reserved name or starts with reserved name followed by a dot
            if (upperDbName.equals(reserved) || upperDbName.startsWith(reserved + ".")) {
                throw new IllegalArgumentException(
                    "Database name is a reserved device name: " + dbName);
            }
        }

        // Normalize path to ensure it doesn't resolve to a directory path
        try {
            final Path normalizedPath = Paths.get(dbName).normalize();

            // Verify the path has no parent (i.e., it's just a filename, not a path with
            // directories)
            if (normalizedPath.getParent() != null) {
                throw new IllegalArgumentException(
                    "Database name contains path components: " + dbName);
            }

            // Verify the filename component is not null.
            final Path fileNamePath = normalizedPath.getFileName();
            if (fileNamePath == null) {
                throw new IllegalArgumentException(
                    "Database name does not contain a valid filename: " + dbName);
            }

            // Verify the filename component matches the input
            // This ensures no path manipulation occurred during normalization
            final String filename = fileNamePath.toString();
            if (!filename.equals(dbName)) {
                throw new IllegalArgumentException(
                    "Database name contains path components: " + dbName);
            }
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException(
                "Database name is not a valid path: " + dbName, e);
        }
    }

    /**
     * @param repositoryManager the repository manager
     * @param dbName the name of the DB to load
     * @return the root object loaded
     */
    protected RootDocument loadRepository(final RepositoryManagerMongo repositoryManager,
        final String dbName) {
        Root root;

        final String filename = buildFileName(dbName);
        final String charset = new CharsetScanner().charset(filename);
        final File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file);
            Reader reader = new InputStreamReader(fis, charset);
            BufferedReader bufferedReader = new BufferedReader(reader);) {
            final GedFile gedFile = new GedFile(filename, dbName, finder, bufferedReader);
            gedFile.readToNext();
            root = createRoot(dbName, filename, gedFile);
        } catch (IOException e) {
            log.warn("Could not read file: {}", filename);
            return null;
        }

        return save(root);
    }

    /**
     * @param dbName   the database name
     * @param filename the file name
     * @param gedFile  the file object
     * @return the root object
     */
    private Root createRoot(final String dbName, final String filename, final GedFile gedFile) {
        final Root root = g2g.create(gedFile);
        root.setString("Root");
        root.setFilename(filename);
        root.setDbName(dbName);
        return root;
    }

    /**
     * Save the root to the database.
     *
     * @param root the root GED object
     * @return the root document
     */
    private RootDocument save(final Root root) {
        final RootDocumentMongo rootdoc = (RootDocumentMongo) toDocConverter
            .createGedDocument(root);
        try {
            rootDocumentRepository.save(rootdoc);
        } catch (DataAccessException e) {
            log.error("Could not save root: {}", root.getDbName(), e);
            return null;
        }
        return rootdoc;
    }

    /**
     * Reset the data.
     *
     * @param repositoryManager the repository manager
     */
    public final void reset(final RepositoryManagerMongo repositoryManager) {
        rootDocumentRepository.deleteAll();
        repositoryManager.reset();
    }

    /**
     * Reload all of the data sets.
     *
     * @param repositoryManager the repository manager
     */
    public final void reloadAll(final RepositoryManagerMongo repositoryManager) {
        final List<String> list = new ArrayList<>();
        for (final RootDocument mongo : rootDocumentRepository.findAll()) {
            list.add(mongo.getDbName());
        }
        reset(repositoryManager);
        for (final String dbname : list) {
            loadDocument(repositoryManager, dbname);
        }
    }

    /**
     * @param repositoryManager the repository manager
     * @return list of name value pairs for the data sets currently loaded
     */
    public final List<Map<String, Object>> details(final RepositoryManagerMongo repositoryManager) {
        return StreamSupport.stream(rootDocumentRepository.findAll().spliterator(), false)
            .map(mongo -> details(repositoryManager, mongo.getDbName()))
            .toList();
    }

    /**
     * @param repositoryManager the repository manager
     * @param dbname name of a dataset
     * @return the name value pairs describing this dataset
     */
    public final Map<String, Object> details(final RepositoryManagerMongo repositoryManager,
        final String dbname) {
        final RootDocument doc = rootDocumentRepository.findByFileAndString(buildFileName(dbname),
            "Root");
        return Map.of("dbname", doc.getDbName(), "filename", doc.getFilename(), "persons",
            repositoryManager.getPersonDocumentRepository().count(doc), "families",
            repositoryManager.getFamilyDocumentRepository().count(doc), "sources",
            repositoryManager.getSourceDocumentRepository().count(doc));
    }
}
