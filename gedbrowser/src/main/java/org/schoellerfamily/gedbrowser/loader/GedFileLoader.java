package org.schoellerfamily.gedbrowser.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    FamilyDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    HeadDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    PersonDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    RootDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    SubmittorDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.
    TrailerDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Dick Schoeller
 */
public class GedFileLoader {
    /** */
    private static boolean needsReset = true;

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
        submittorDocumentRepository; // NOPMD

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
     * @param dbName the name of the database to load
     * @return the root object of the database
     */
    public final GedObject load(final String dbName) {
        final String filename = buildFileName(dbName);
        final RootDocument rootDocument =
                rootDocumentRepository.findByFileAndString(filename, "Root");
        GedObject root;
        if (rootDocument != null) {
            root = rootDocument.getGedObject();
            return root;
        }

        root = loadRepository(dbName);

        return root;
    }

    /**
     * @param dbName the name of the DB
     * @return the derived filename
     */
    private String buildFileName(final String dbName) {
        return gedbrowserHome + "/" + dbName + ".ged";
    }

    /**
     * @param dbName the name of the DB to load
     * @return the root object loaded
     */
    private Root loadRepository(final String dbName) {
        Root root;

        final String filename = buildFileName(dbName);

        final File file = new File(filename);
        try (final FileInputStream fis = new FileInputStream(file);
                final Reader reader = new InputStreamReader(fis, "UTF-8");
                final BufferedReader bufferedReader =
                        new BufferedReader(reader);) {
            final GedFile gedFile =
                    new GedFile(filename, dbName, finder, bufferedReader);
            gedFile.readToNext();
            root = (Root) gedFile.createGedObject((AbstractGedLine) null);
            root.setString("Root");
            root.setFilename(filename);
            root.setDbName(dbName);
        } catch (IOException e) {
            Logger.getGlobal().severe("Could not read file: " + filename);
            root = null;
        }

        final RootDocumentMongo rootdoc =
                (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                        .createGedDocument(root);
        rootDocumentRepository.save(rootdoc);

        return root;
    }

    /**
     * Reset the data.
     */
    public final void reset() {
        if (checkNeedsReset()) {
            rootDocumentRepository.deleteAll();
            personDocumentRepository.deleteAll();
            familyDocumentRepository.deleteAll();
            sourceDocumentRepository.deleteAll();
            headDocumentRepository.deleteAll();
            submittorDocumentRepository.deleteAll();
            trailerDocumentRepository.deleteAll();
        }
    }

    /**
     * Check whether to reset and then clear the flag.
     *
     * @return if needs reset
     */
    public static final boolean checkNeedsReset() {
        final boolean retVal = needsReset;
        if (retVal) {
            needsReset = false;
        }
        return retVal;
    }
}
