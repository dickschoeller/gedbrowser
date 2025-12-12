package org.schoellerfamily.gedbrowser.persistence.mongo.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

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

    private final RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private final String gedbrowserHome;

    /**
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public RootDocument loadDocument(final RepositoryManagerMongo repositoryManager, final String dbName) {
        log.info("entering loadDocument({})", dbName);
        final String filename = buildFileName(dbName);
        final RootDocument rootDocument = rootDocumentRepository
                .findByFileAndString(filename, "Root");
        if (rootDocument == null) {
            return loadRepository(repositoryManager, dbName);
        }

        return rootDocument;
    }

    /**
     * @param dbName the name of the DB
     * @return the derived filename
     */
    protected String buildFileName(final String dbName) {
        return gedbrowserHome + "/" + dbName + ".ged";
    }

    /**
     * @param dbName the name of the DB to load
     * @return the root object loaded
     */
    protected RootDocument loadRepository(final RepositoryManagerMongo repositoryManager, final String dbName) {
        Root root;

        final String filename = buildFileName(dbName);
        final String charset = new CharsetScanner().charset(filename);
        final File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file);
                Reader reader = new InputStreamReader(fis, charset);
                BufferedReader bufferedReader = new BufferedReader(reader);) {
            final GedFile gedFile = new GedFile(filename, dbName, finder,
                    bufferedReader);
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
    private Root createRoot(final String dbName, final String filename,
            final GedFile gedFile) {
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
        final RootDocumentMongo rootdoc = (RootDocumentMongo) toDocConverter.createGedDocument(root);
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
     */
    public final void reset(final RepositoryManagerMongo repositoryManager) {
        rootDocumentRepository.deleteAll();
        repositoryManager.reset();
    }

    /**
     * Reload all of the data sets.
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
     * @return list of name value pairs for the data sets currently loaded
     */
    public final List<Map<String, Object>> details(final RepositoryManagerMongo repositoryManager) {
        return StreamSupport.stream(rootDocumentRepository.findAll().spliterator(), false)
            .map(mongo -> details(repositoryManager, mongo.getDbName()))
            .toList();
    }

    /**
     * @param dbname name of a dataset
     * @return the name value pairs describing this dataset
     */
    public final Map<String, Object> details(final RepositoryManagerMongo repositoryManager,
        final String dbname) {
        final RootDocument doc = rootDocumentRepository
                .findByFileAndString(buildFileName(dbname), "Root");
        return Map.of(
            "dbname", doc.getDbName(),
            "filename", doc.getFilename(),
            "persons", repositoryManager.getPersonDocumentRepository().count(doc),
            "families", repositoryManager.getFamilyDocumentRepository().count(doc),
            "sources", repositoryManager.getSourceDocumentRepository().count(doc)
        );
    }
}
