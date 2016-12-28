package org.schoellerfamily.geoservice.persistence.mongo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.geoservice.persistence.GeoCodeBasic;
import org.schoellerfamily.geoservice.persistence.GeoCodeItem;
import org.schoellerfamily.geoservice.persistence.domain.GeoDocument;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongo;
import org.schoellerfamily.geoservice.persistence.mongo.domain.GeoDocumentMongoFactory;
import org.schoellerfamily.geoservice.persistence.mongo.repository.GeoDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class implements the cache that allows applications to work with the
 * Google geocoding APIs, without running the queries per day limitations.
 *
 * TODO rework singleton management to make this be Spring loadable.
 *
 * @author Dick Schoeller
 */
@SuppressWarnings({ "PMD.CommentSize", "PMD.TooManyMethods" })
public final class GeoCodeMongo extends GeoCodeBasic {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GeoDocumentRepositoryMongo geoDocumentRepository;

    /**
     * Public constructor. Using Spring to manage as a singleton.
     */
    public GeoCodeMongo() {
        super();
        logger.debug("Initializing GeoCodeCache");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        geoDocumentRepository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load(final String filename) {
        logger.debug("Loading the cache from places file: " + filename);
        try (
            InputStream fis = new FileInputStream(filename);
        ) {
            load(fis);
        } catch (IOException e) {
            logger.error("Problem reading places file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void load(final InputStream istream) {
        logger.debug("Loading the cache from input stream");
        String line;
        try (
            InputStreamReader isr =
                    new InputStreamReader(istream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                final String[] splitLine = line.split("[|]", 4);
                if (splitLine.length > 2) {
                    find(splitLine[0], splitLine[1]);
                } else {
                    find(splitLine[0]);
                }
            }
        } catch (IOException e) {
            logger.error("Problem reading places stream", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<? extends GeoDocument> findAllDocuments() {
        return geoDocumentRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        final long count = geoDocumentRepository.count();
        logger.debug("Geocode cache contains " + count + " entries");
        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument create(final GeoCodeItem item) {
        final GeoDocumentMongo geoDocument =
                (GeoDocumentMongo) GeoDocumentMongoFactory.getInstance()
                        .createGeoDocument(item);
        return geoDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument addDocument(final GeoDocument document) {
        if (document == null || document.getName() == null) {
            return document;
        }
        geoDocumentRepository.save((GeoDocumentMongo) document);
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument getDocument(final String placeName) {
        if (placeName == null) {
            return null;
        }
        return geoDocumentRepository.find(placeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GeoDocument deleteDocument(final String placeName) {
        final GeoDocument document = getDocument(placeName);
        if (document != null) {
            geoDocumentRepository.delete(placeName);
        }
        return document;
    }
}
