package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.finder.FinderStrategy;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.ExcessiveImports")
public final class RepositoryFinderMongo implements FinderStrategy {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient PersonDocumentRepositoryMongo personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepositoryMongo familyDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepositoryMongo sourceDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepositoryMongo headDocumentRepository;

    /** */
    @Autowired
    private transient SubmittorDocumentRepositoryMongo
        submittorDocumentRepository;

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;

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
        CLASSES.add(Submittor.class);
        CLASSES.add(Trailer.class);
    }

    /**
     * Get the map that we need to go from class to repository.
     *
     * @return the map
     */
    private Map<Class<? extends GedObject>,
    FindableDocument<? extends GedObject,
            ? extends GedDocument<?>>> getRepoMap() {
        /**
         * Holds the connections between ged classes and repositories.
         */
        final Map<Class<? extends GedObject>,
            FindableDocument<? extends GedObject,
                    ? extends GedDocument<?>>> repoMap = new HashMap<>();
        repoMap.put(Family.class, familyDocumentRepository);
        repoMap.put(Head.class, headDocumentRepository);
        repoMap.put(Person.class, personDocumentRepository);
        repoMap.put(Source.class, sourceDocumentRepository);
        repoMap.put(Submittor.class, submittorDocumentRepository);
        repoMap.put(Trailer.class, trailerDocumentRepository);
        return repoMap;
    }

    /**
     * @return the repository
     */
    public PersonDocumentRepositoryMongo getPersonDocumentRepository() {
        return personDocumentRepository;
    }

    /**
     * @return the repository
     */
    public FamilyDocumentRepositoryMongo getFamilyDocumentRepository() {
        return familyDocumentRepository;
    }

    /**
     * @return the repository
     */
    public SourceDocumentRepositoryMongo getSourceDocumentRepository() {
        return sourceDocumentRepository;
    }

    /**
     * @return the repository
     */
    public HeadDocumentRepositoryMongo getHeadDocumentRepository() {
        return headDocumentRepository;
    }

    /**
     * @return the repository
     */
    public SubmittorDocumentRepositoryMongo getSubmittorDocumentRepository() {
        return submittorDocumentRepository;
    }

    /**
     * @return the repository
     */
    public TrailerDocumentRepositoryMongo getTrailerDocumentRepository() {
        return trailerDocumentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GedObject find(final GedObject owner, final String str) {
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
    public <T extends GedObject> T find(final GedObject owner,
            final String str, final Class<T> clazz) {
        if (!(owner instanceof Root)) {
            throw new IllegalArgumentException("Owner must be root");
        }
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = getRepoMap().get(clazz);
        if (repo == null) {
            return null;
        }
        final Root root = (Root) owner;
        final RootDocumentMongo rootDocument =
                (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                .createGedDocument(root);
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
    public String getFilename(final GedObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheFilename();
        }
        return owner.getFilename();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDbName(final GedObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheDbName();
        }
        return owner.getDbName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(final GedObject owner, final GedObject gob) {
        try {
            logger.debug("Starting insert: " + gob.getString());
            final GedDocumentMongo<?> gedDoc =
                    GedDocumentMongoFactory.getInstance().
                    createGedDocument(gob);
            final GedDocumentMongoVisitor visitor = new SaveVisitor(this);
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
    public Collection<Person> findBySurname(final GedObject owner,
            final String surname) {
        logger.info("Starting findBySurname");
        final List<Person> persons = new ArrayList<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                            .createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    personDocumentRepository
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
    public Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith) {
        logger.info("Starting findBySurnamesBeginWith");
        final Set<String> surnames = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                            .createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    personDocumentRepository
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
    public Collection<String> findSurnameInitialLetters(final GedObject owner) {
        logger.info("Starting findSurnameInitialLetters");
        final Set<String> matches = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument =
                    (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                            .createGedDocument(root);
            final Iterable<PersonDocument> personDocuments =
                    personDocumentRepository.findByRoot(rootDocument);
            for (final PersonDocument personDocument : personDocuments) {
                final String firstLetter = personDocument.getSurname()
                        .substring(0, 1);
                matches.add(firstLetter);
            }
        }
        logger.info("Ending findSurnameInitialLetters");
        return matches;
    }
}
