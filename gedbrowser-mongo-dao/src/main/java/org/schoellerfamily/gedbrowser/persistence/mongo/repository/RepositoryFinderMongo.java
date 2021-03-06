package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.FinderObject;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
public final class RepositoryFinderMongo
        implements FinderStrategy {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /**
     * Ordered list of classes to process. This order represents the
     * most likely search order.
     */
    private static final List<Class<? extends GedObject>> CLASSES =
            new ArrayList<>();
    static {
        CLASSES.add(Person.class);
        CLASSES.add(Family.class);
        CLASSES.add(Source.class);
        CLASSES.add(Head.class);
        CLASSES.add(Note.class);
        CLASSES.add(Submission.class);
        CLASSES.add(Submitter.class);
        CLASSES.add(Trailer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedObject find(final FinderObject owner, final String str) {
        for (final Class<? extends GedObject> clazz : CLASSES) {
            final GedObject ged = find(owner, str, clazz);
            if (ged != null) {
                return ged;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends GedObject> T find(final FinderObject owner,
            final String str, final Class<T> clazz) {
        if (!(owner instanceof Root)) {
            throw new IllegalArgumentException("Owner must be root");
        }
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(clazz);
        if (repo == null) {
            return null;
        }
        final Root root = (Root) owner;
        final RootDocumentMongo rootDocument =
                (RootDocumentMongo) toDocConverter.createGedDocument(root);
        final GedDocument<?> document =
                repo.findByRootAndString(rootDocument, str);
        if (document == null) {
            return null;
        }
        return clazz.cast(document.getGedObject());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFilename(final FinderObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheFilename();
        }
        return owner.getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDbName(final FinderObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheDbName();
        }
        return owner.getDbName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final FinderObject owner, final FinderObject fob) {
        final GedObject gob = (GedObject) fob;
        try {
            logger.debug("Starting insert: " + gob.getString());
            final GedDocumentMongo<?> gedDoc =
                    toDocConverter.createGedDocument(gob);
            final TopLevelGedDocumentMongoVisitor visitor =
                    new SaveVisitor(repositoryManager);
            gedDoc.accept(visitor);
        } catch (DataAccessException e) {
            logger.error("Error saving: " + gob.getString(), e);
        }
        logger.debug("Ending insert: " + gob.getString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Person> findBySurname(final FinderObject owner,
            final String surname) {
        logger.info("Starting findBySurname");
        final List<Person> persons = new ArrayList<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) toDocConverter.createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    repositoryManager.getPersonDocumentRepository()
                    .findByRootAndSurname(rootDocument, surname);
            for (final PersonDocument personDocument : personDocuments) {
                persons.add(personDocument.getGedObject());
            }
        }
        logger.info("Ending findBySurname");
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final FinderObject owner,
            final String beginsWith) {
        logger.info("Starting findBySurnamesBeginWith");
        final Set<String> surnames = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) toDocConverter.createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    repositoryManager.getPersonDocumentRepository()
                    .findByRootAndSurnameBeginsWith(rootDocument, beginsWith);
            for (final PersonDocument personDocument : personDocuments) {
                surnames.add(personDocument.getSurname());
            }
        }
        logger.info("Ending findBySurnamesBeginWith");
        return surnames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findSurnameInitialLetters(
            final FinderObject owner) {
        logger.info("Starting findSurnameInitialLetters");
        final Set<String> matches = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) toDocConverter.createGedDocument(root);
            final Iterable<PersonDocument> personDocuments =
                    repositoryManager.getPersonDocumentRepository().
                        findAll(rootDocument);
            for (final PersonDocument personDocument : personDocuments) {
                final String firstLetter = personDocument.getSurname()
                        .substring(0, 1);
                matches.add(firstLetter);
            }
        }
        logger.info("Ending findSurnameInitialLetters");
        return matches;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
            final Class<T> clazz) {
        logger.info("Starting find all of type");
        if (!(owner instanceof Root)) {
            throw new IllegalArgumentException("Owner must be root");
        }
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(clazz);
        if (repo == null) {
            return null;
        }
        final RootDocumentMongo rootDocument = (RootDocumentMongo)
                toDocConverter.createGedDocument((Root) owner);
        final Collection<T> matches = new ArrayList<>();
        for (final GedDocument<?> document : repo.findAll(rootDocument)) {
            matches.add((T) document.getGedObject());
        }
        logger.info("Ending find all of type");
        return matches;
    }
}
