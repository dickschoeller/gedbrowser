package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.StreamSupport;

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
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.visitor.TopLevelGedDocumentMongoVisitor;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@Component
@SuppressWarnings("PMD.ExcessiveImports")
@RequiredArgsConstructor
@Slf4j
public final class RepositoryFinderMongo implements FinderStrategy {
    /** */
    private final RepositoryManagerMongo repositoryManager;

    /** */
    private final GedObjectToGedDocumentMongoConverter toDocConverter;

    private static final List<Class<? extends GedObject>> CLASSES = List.of(Person.class,
        Family.class, Source.class, Head.class, Note.class, Submission.class, Submitter.class,
        Trailer.class);

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

    @Override
    public <T extends GedObject> T find(final FinderObject owner, final String str,
        final Class<T> clazz) {
        if (!(owner instanceof Root)) {
            throw new IllegalArgumentException("Owner must be root");
        }
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>> repo =
            repositoryManager.get(clazz);
        if (repo == null) {
            return null;
        }
        final Root root = (Root) owner;
        final RootDocumentMongo rootDocument = (RootDocumentMongo) toDocConverter
            .createGedDocument(root);
        final GedDocument<?> document = repo.findByRootAndString(rootDocument, str);
        if (document == null) {
            return null;
        }
        return clazz.cast(document.getGedObject());
    }

    @Override
    public String getFilename(final FinderObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheFilename();
        }
        return owner.getFilename();
    }

    @Override
    public String getDbName(final FinderObject owner) {
        if (owner instanceof Root) {
            return ((Root) owner).getTheDbName();
        }
        return owner.getDbName();
    }

    @Override
    public void insert(final FinderObject owner, final FinderObject fob) {
        final GedObject gob = (GedObject) fob;
        try {
            log.debug("Starting insert: {}", gob.getString());
            final GedDocumentMongo<?> gedDoc = toDocConverter.createGedDocument(gob);
            final TopLevelGedDocumentMongoVisitor visitor = new SaveVisitor(repositoryManager);
            gedDoc.accept(visitor);
        } catch (DataAccessException e) {
            log.error("Error saving: {}", gob.getString(), e);
        }
        log.debug("Ending insert: {}", gob.getString());
    }

    @Override
    public Collection<Person> findBySurname(final FinderObject owner, final String surname) {
        log.info("Starting findBySurname");
        if (!(owner instanceof Root)) {
            log.info("Ending findBySurname");
            return List.of();
        }
        final Root root = (Root) owner;
        final RootDocumentMongo rootDocument = (RootDocumentMongo) toDocConverter
            .createGedDocument(root);
        final Collection<PersonDocument> personDocuments = repositoryManager
            .getPersonDocumentRepository()
            .findByRootAndSurname(rootDocument, surname);
        final List<Person> persons = personDocuments.stream()
            .map(PersonDocument::getGedObject)
            .toList();
        log.info("Ending findBySurname");
        return persons;
    }

    @Override
    public Collection<String> findBySurnamesBeginWith(final FinderObject owner,
        final String beginsWith) {
        log.info("Starting findBySurnamesBeginWith");
        final Set<String> surnames = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument = (RootDocumentMongo) toDocConverter
                .createGedDocument(root);
            final Collection<PersonDocument> personDocuments = repositoryManager
                .getPersonDocumentRepository()
                .findByRootAndSurnameBeginsWith(rootDocument, beginsWith);
            for (final PersonDocument personDocument : personDocuments) {
                surnames.add(personDocument.getSurname());
            }
        }
        log.info("Ending findBySurnamesBeginWith");
        return surnames;
    }

    @Override
    public Collection<String> findSurnameInitialLetters(final FinderObject owner) {
        log.info("Starting findSurnameInitialLetters");
        final Set<String> matches = new TreeSet<>();
        if (owner instanceof Root) {
            final Root root = (Root) owner;
            final RootDocumentMongo rootDocument = (RootDocumentMongo) toDocConverter
                .createGedDocument(root);
            final Iterable<PersonDocument> personDocuments = repositoryManager
                .getPersonDocumentRepository()
                .findAll(rootDocument);
            for (final PersonDocument personDocument : personDocuments) {
                final String firstLetter = personDocument.getSurname().substring(0, 1);
                matches.add(firstLetter);
            }
        }
        log.info("Ending findSurnameInitialLetters");
        return matches;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends GedObject> Collection<T> find(final FinderObject owner,
        final Class<T> clazz) {
        log.info("Starting find all of type");
        if (!(owner instanceof Root)) {
            throw new IllegalArgumentException("Owner must be root");
        }
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>> repo =
            repositoryManager.get(clazz);
        if (repo == null) {
            return List.of();
        }
        final RootDocumentMongo rootDocument = (RootDocumentMongo) toDocConverter
            .createGedDocument((Root) owner);
        final Collection<T> matches = StreamSupport
            .stream(repo.findAll(rootDocument).spliterator(), false)
            .map(document -> (T) document.getGedObject())
            .toList();
        log.info("Ending find all of type");
        return matches;
    }
}
