package org.schoellerfamily.gedbrowser.persistence.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmittorDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.TrailerDocument;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public final class RepositoryFinder implements FinderStrategy {
    /** */
    @Autowired
    private transient PersonDocumentRepository personDocumentRepository;

    /** */
    @Autowired
    private transient FamilyDocumentRepository familyDocumentRepository;

    /** */
    @Autowired
    private transient SourceDocumentRepository sourceDocumentRepository;

    /** */
    @Autowired
    private transient HeadDocumentRepository headDocumentRepository;

    /** */
    @Autowired
    private transient SubmittorDocumentRepository
        submittorDocumentRepository; // NOPMD

    /** */
    @Autowired
    private transient TrailerDocumentRepository trailerDocumentRepository;

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
        final RootDocument rootDocument = (RootDocument) GedDocumentFactory
                .getInstance().createGedDocument(root);
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
        final GedDocument<?> gedDoc = GedDocumentFactory.getInstance().
                createGedDocument(gob);
        if (gedDoc instanceof PersonDocument) {
            personDocumentRepository.save((PersonDocument) gedDoc);
        } else if (gedDoc instanceof FamilyDocument) {
            familyDocumentRepository.save((FamilyDocument) gedDoc);
        } else if (gedDoc instanceof SourceDocument) {
            sourceDocumentRepository.save((SourceDocument) gedDoc);
        } else if (gedDoc instanceof HeadDocument) {
            headDocumentRepository.save((HeadDocument) gedDoc);
        } else if (gedDoc instanceof SubmittorDocument) {
            submittorDocumentRepository.save((SubmittorDocument) gedDoc);
        } else if (gedDoc instanceof TrailerDocument) {
            trailerDocumentRepository.save((TrailerDocument) gedDoc);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Person> findBySurname(final GedObject owner,
            final String surname) {
        final List<Person> persons = new ArrayList<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocument rootDocument = (RootDocument) GedDocumentFactory
                    .getInstance().createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    personDocumentRepository
                    .findByRootAndSurname(rootDocument, surname);
            for (final PersonDocument personDocument : personDocuments) {
                persons.add(personDocument.getGedObject());
            }
        }
        return persons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findBySurnamesBeginWith(final GedObject owner,
            final String beginsWith) {
        final Set<String> surnames = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocument rootDocument = (RootDocument) GedDocumentFactory
                    .getInstance().createGedDocument(root);
            final Collection<PersonDocument> personDocuments =
                    personDocumentRepository
                    .findByRootAndSurnameBeginsWith(rootDocument, beginsWith);
            for (final PersonDocument personDocument : personDocuments) {
                surnames.add(personDocument.getSurname());
            }
        }
        return surnames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> findSurnameInitialLetters(final GedObject owner) {
        final Set<String> matches = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocument rootDocument = (RootDocument) GedDocumentFactory
                    .getInstance().createGedDocument(root);
            final Iterable<PersonDocument> personDocuments =
                    personDocumentRepository.findByRoot(rootDocument);
            for (final PersonDocument personDocument : personDocuments) {
                final String firstLetter = personDocument.getSurname()
                        .substring(0, 1);
                matches.add(firstLetter);
            }
        }
        return matches;
    }
}
