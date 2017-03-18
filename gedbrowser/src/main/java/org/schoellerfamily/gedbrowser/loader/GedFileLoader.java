package org.schoellerfamily.gedbrowser.loader;

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
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SubmittorDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.TrailerDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
public class GedFileLoader {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Autowired
    private transient PersonDocumentRepositoryMongo personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepositoryMongo familyDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepositoryMongo headDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepositoryMongo sourceDocumentRepository;

    /** */
    @Autowired
    private transient SubmittorDocumentRepositoryMongo
        submittorDocumentRepository;

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;

    /** */
    @Autowired
    private transient FinderStrategy finder;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Constructor.
     */
    public GedFileLoader() {
        // Empty constructor.
    }

    /**
     * @param dbName
     *            the name of the database to load
     * @return the root object of the database
     */
    public final GedObject load(final String dbName) {
        final String filename = buildFileName(dbName);
        final RootDocument rootDocument = rootDocumentRepository
                .findByFileAndString(filename, "Root");
        GedObject root;
        if (rootDocument != null) {
            root = rootDocument.getGedObject();
            return root;
        }

        root = loadRepository(dbName);

        return root;
    }

    /**
     * @param dbName
     *            the name of the DB
     * @return the derived filename
     */
    private String buildFileName(final String dbName) {
        return gedbrowserHome + "/" + dbName + ".ged";
    }

    /**
     * @param dbName
     *            the name of the DB to load
     * @return the root object loaded
     */
    private Root loadRepository(final String dbName) {
        Root root;

        final String filename = buildFileName(dbName);

        final File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file);
                Reader reader = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);) {
            final GedFile gedFile = new GedFile(filename, dbName, finder,
                    bufferedReader);
            gedFile.readToNext();
            root = (Root) gedFile.createGedObject((AbstractGedLine) null);
            root.setString("Root");
            root.setFilename(filename);
            root.setDbName(dbName);
        } catch (IOException e) {
            logger.error("Could not read file: " + filename, e);
            return null;
        }

        final RootDocumentMongo rootdoc =
                (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                    .createGedDocument(root);
        try {
            rootDocumentRepository.save(rootdoc);
        } catch (DataAccessException e) {
            logger.error("Could not save root: " + root.getDbName(), e);
        }

        return root;
    }

    /**
     * Reset the data.
     */
    public final void reset() {
        rootDocumentRepository.deleteAll();
        personDocumentRepository.deleteAll();
        familyDocumentRepository.deleteAll();
        sourceDocumentRepository.deleteAll();
        headDocumentRepository.deleteAll();
        submittorDocumentRepository.deleteAll();
        trailerDocumentRepository.deleteAll();
    }

    /**
     * Reload all of the data sets.
     */
    public final void reloadAll() {
        final List<String> list = new ArrayList<>();
        for (final RootDocument mongo : rootDocumentRepository.findAll()) {
            list.add(mongo.getDbName());
        }
        reset();
        for (final String dbname : list) {
            load(dbname);
        }
    }

    /**
     * @return list of name value pairs for the data sets currently loaded
     */
    public final List<Map<String, Object>> details() {
        final List<Map<String, Object>> list = new ArrayList<>();
        for (final RootDocument mongo : rootDocumentRepository.findAll()) {
            list.add(details(mongo.getDbName()));
        }
        return list;
    }

    /**
     * @param dbname
     *            name of a dataset
     * @return the name value pairs describing this dataset
     */
    public final Map<String, Object> details(final String dbname) {
        final Map<String, Object> map = new HashMap<>();
        final RootDocument doc = rootDocumentRepository
                .findByFileAndString(buildFileName(dbname), "Root");
        map.put("dbname", doc.getDbName());
        map.put("filename", doc.getFilename());
        map.put("persons", personDocumentRepository.count(doc));
        map.put("families", familyDocumentRepository.count(doc));
        map.put("sources", sourceDocumentRepository.count(doc));
        return map;
    }
}
