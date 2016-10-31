package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submittor;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    FamilyDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    HeadDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SourceDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    SubmittorDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    TrailerDocumentMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class RepositoryFinderMongo implements FinderStrategy {
    /** Logger. */
    private static final Logger LOGGER = Logger
            .getLogger(RepositoryFinderMongo.class.getName());

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
        submittorDocumentRepository; // NOPMD

    /** */
    @Autowired
    private transient TrailerDocumentRepositoryMongo trailerDocumentRepository;

    @Override
    public GedObject find(final GedObject owner, final String str) {
        GedObject ged = find(owner, str, Person.class);
        if (ged != null) {
            return ged;
        }
        ged = find(owner, str, Family.class);
        if (ged != null) {
            return ged;
        }
        ged = find(owner, str, Source.class);
        if (ged != null) {
            return ged;
        }
        ged = find(owner, str, Head.class);
        if (ged != null) {
            return ged;
        }
        ged = find(owner, str, Submittor.class);
        if (ged != null) {
            return ged;
        }
        ged = find(owner, str, Trailer.class);
        if (ged != null) {
            return ged;
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
        final Root root = (Root) owner;
        final RootDocumentMongo rootDocument =
                (RootDocumentMongo) GedDocumentMongoFactory.getInstance()
                        .createGedDocument(root);
        if (clazz.equals(Family.class)) {
            final FamilyDocument document = familyDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        } else if (clazz.equals(Head.class)) {
            final HeadDocument document = headDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        } else if (clazz.equals(Person.class)) {
            final PersonDocument document = personDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        } else if (clazz.equals(Source.class)) {
            final SourceDocument document = sourceDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        } else if (clazz.equals(Submittor.class)) {
            final SubmittorDocument document = submittorDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        } else if (clazz.equals(Trailer.class)) {
            final TrailerDocument document = trailerDocumentRepository.
                    findByRootAndString(
                            rootDocument, str);
            if (document == null) {
                return null;
            }
            return clazz.cast(document.getGedObject());
        }
        return null;
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
        LOGGER.entering("RepositoryFinder", "insert");
        final GedDocument<?> gedDoc = GedDocumentMongoFactory.getInstance().
                createGedDocument(gob);
        if (gedDoc instanceof PersonDocumentMongo) {
            personDocumentRepository.save((PersonDocumentMongo) gedDoc);
        } else if (gedDoc instanceof FamilyDocumentMongo) {
            familyDocumentRepository.save((FamilyDocumentMongo) gedDoc);
        } else if (gedDoc instanceof SourceDocumentMongo) {
            sourceDocumentRepository.save((SourceDocumentMongo) gedDoc);
        } else if (gedDoc instanceof HeadDocumentMongo) {
            headDocumentRepository.save((HeadDocumentMongo) gedDoc);
        } else if (gedDoc instanceof SubmittorDocumentMongo) {
            submittorDocumentRepository.save((SubmittorDocumentMongo) gedDoc);
        } else if (gedDoc instanceof TrailerDocumentMongo) {
            trailerDocumentRepository.save((TrailerDocumentMongo) gedDoc);
        }
        LOGGER.exiting("RepositoryFinder", "insert");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Person> findBySurname(final GedObject owner,
            final String surname) {
        LOGGER.entering("RepositoryFinder", "findBySurname");
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
        LOGGER.exiting("RepositoryFinder", "findBySurname");
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith) {
        LOGGER.entering("RepositoryFinder", "findBySurnamesBeginWith");
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
        LOGGER.exiting("RepositoryFinder", "findBySurnamesBeginWith");
        return surnames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findSurnameInitialLetters(final GedObject owner) {
        LOGGER.entering("RepositoryFinder", "findSurnameInitialLetters");
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
        LOGGER.exiting("RepositoryFinder", "findSurnameInitialLetters");
        return matches;
    }
}
