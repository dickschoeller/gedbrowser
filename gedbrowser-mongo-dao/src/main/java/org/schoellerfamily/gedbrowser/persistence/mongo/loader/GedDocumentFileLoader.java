package org.schoellerfamily.gedbrowser.persistence.mongo.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.reader.GedFile;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;

/**
 * @author Dick Schoeller
 */
public class GedDocumentFileLoader {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /**
     * Manages the persistence repository.
     */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Implements finding other objects.
     */
    @Autowired
    private transient FinderStrategy finder;

    /**
     * Converts AbstractGedLine hierarchy to GedObject hierarchy.
     */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    /**
     * Implements conversion from GedObject to GedDocumentMongo.
     */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Constructor.
     */
    public GedDocumentFileLoader() {
        // Empty constructor.
    }

    /**
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public RootDocument loadDocument(final String dbName) {
        logger.info("entering loadDocument(" + dbName + ")");
        final String filename = buildFileName(dbName);
        final RootDocument rootDocument =
                repositoryManager.getRootDocumentRepository()
                    .findByFileAndString(filename, "Root");
        if (rootDocument == null) {
            return loadRepository(dbName);
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
    protected RootDocument loadRepository(final String dbName) {
        Root root;

        final String filename = buildFileName(dbName);

        final File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file);
                Reader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);) {
            final GedFile gedFile = new GedFile(filename, dbName, finder,
                    bufferedReader);
            gedFile.readToNext();
            root = createRoot(dbName, filename, gedFile);
        } catch (IOException e) {
            logger.warn("Could not read file: " + filename);
            return null;
        }

        return save(root);
    }

    /**
     * @param dbName the database name
     * @param filename the file name
     * @param gedFile the file object
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
     * @param root the root GED object
     * @return the root document
     */
    private RootDocument save(final Root root) {
        final RootDocumentMongo rootdoc =
                (RootDocumentMongo) toDocConverter.createGedDocument(root);
        try {
            repositoryManager.getRootDocumentRepository().save(rootdoc);
        } catch (DataAccessException e) {
            logger.error("Could not save root: " + root.getDbName(), e);
        }
        return rootdoc;
    }

    /**
     * Reset the data.
     */
    public final void reset() {
        repositoryManager.reset();
    }

    /**
     * Reload all of the data sets.
     */
    public final void reloadAll() {
        final List<String> list = new ArrayList<>();
        for (final RootDocument mongo : repositoryManager
                .getRootDocumentRepository().findAll()) {
            list.add(mongo.getDbName());
        }
        reset();
        for (final String dbname : list) {
            loadDocument(dbname);
        }
    }

    /**
     * @return list of name value pairs for the data sets currently loaded
     */
    public final List<Map<String, Object>> details() {
        final List<Map<String, Object>> list = new ArrayList<>();
        for (final RootDocument mongo : repositoryManager
                .getRootDocumentRepository().findAll()) {
            list.add(details(mongo.getDbName()));
        }
        return list;
    }

    /**
     * @param dbname name of a dataset
     * @return the name value pairs describing this dataset
     */
    public final Map<String, Object> details(final String dbname) {
        final Map<String, Object> map = new HashMap<>();
        final RootDocument doc = repositoryManager.getRootDocumentRepository()
                .findByFileAndString(buildFileName(dbname), "Root");
        map.put("dbname", doc.getDbName());
        map.put("filename", doc.getFilename());
        putCounts(map, doc);
        return map;
    }

    /**
     * @param map the map with details
     * @param doc the root document
     */
    private void putCounts(final Map<String, Object> map,
            final RootDocument doc) {
        map.put("persons",
                repositoryManager.getPersonDocumentRepository().count(doc));
        map.put("families",
                repositoryManager.getFamilyDocumentRepository().count(doc));
        map.put("sources",
                repositoryManager.getSourceDocumentRepository().count(doc));
    }
}
