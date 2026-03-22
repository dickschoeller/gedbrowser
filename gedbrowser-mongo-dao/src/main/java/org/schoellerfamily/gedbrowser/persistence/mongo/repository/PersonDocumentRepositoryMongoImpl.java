package org.schoellerfamily.gedbrowser.persistence.mongo.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.util.PersonComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.
    PersonDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedDocumentMongoToGedObjectConverter;
import org.schoellerfamily.gedbrowser.persistence.repository.
    FindableByNameDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



/**
 * Represents person document repository mongo impl for persistence operations.
 *
 * @author Richard Schoeller
 */
@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("PMD.TooManyMethods")
public final class PersonDocumentRepositoryMongoImpl implements
        FindableByNameDocument<Person, PersonDocument>,
        LastId<PersonDocumentMongo> {
    /**
     * The s u r n a m e constant.
     */
    private static final String SURNAME = "surname";
    /**
     * The mongo template value.
     */
    private final MongoTemplate mongoTemplate;
    /**
     * The to obj converter value.
     */
    private final GedDocumentMongoToGedObjectConverter toObjConverter;
    /**
     * Finds the by file and string.
     *
     * @param filename the filename to use
     * @return the resulting person document
     */
    @Override
    public PersonDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and(FILENAME).is(filename));
        final PersonDocument personDocument = mongoTemplate.findOne(
                searchQuery, PersonDocumentMongo.class);
        if (personDocument == null) {
            return null;
        }
        final Person person =
                (Person) toObjConverter.createGedObject(null, personDocument);
        personDocument.setGedObject(person);
        return personDocument;
    }

    /**
     * Finds the by root and string.
     *
     * @param rootDocument the root document
     * @param string the string
     * @return the resulting person document
     */
    @Override
    public PersonDocument findByRootAndString(
            final RootDocument rootDocument, final String string) {
        final PersonDocument personDocument = findByFileAndString(
                rootDocument.getFilename(), string);
        if (personDocument == null) {
            return null;
        }
        final Person person = personDocument.getGedObject();
        person.setParent(rootDocument.getGedObject());
        return personDocument;
    }

    /**
     * Finds by file and surname.
     *
     * @param filename the filename to use
     * @param surname the surname to use
     * @return the resulting collection of person document
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurname(
            final String filename, final String surname) {
        if (filename == null || surname == null) {
            return List.of();
        }
        final Query searchQuery =
                new Query(Criteria.where(SURNAME).is(surname)
                        .and(FILENAME).is(filename));
        final List<PersonDocumentMongo> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
        createGedObjects(personDocuments);
        return copy(personDocuments);
    }

    /**
     * Finds the by root and surname.
     *
     * @param rootDocument the root document
     * @param surname the surname to use
     * @return the resulting collection
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurname(
            final RootDocument rootDocument, final String surname) {
        if (rootDocument == null || surname == null) {
            return List.of();
        }
        final Collection<PersonDocument> personDocuments = findByFileAndSurname(
                rootDocument.getFilename(), surname);
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        return personDocuments;
    }

    /**
     * Finds the by file and surname begins with.
     *
     * @param filename the filename to use
     * @param beginsWith the begins with
     * @return the resulting collection
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurnameBeginsWith(
            final String filename, final String beginsWith) {
        if (filename == null) {
            return List.of();
        }
        final List<PersonDocumentMongo> personDocuments;
        if (StringUtils.isEmpty(beginsWith) || "?".equals(beginsWith)) {
            personDocuments = queryUnknownSurname(filename);
        } else {
            personDocuments = querySurnameBeginsWith(filename, beginsWith);
        }
        createGedObjects(personDocuments);
        return copy(personDocuments);
    }

    /**
     * Performs query unknown surname.
     *
     * @param filename the filename of the dataset to search
     * @return the list of matches
     */
    private List<PersonDocumentMongo> queryUnknownSurname(
            final String filename) {
        final Query emptyQuery = new Query(
                Criteria.where(FILENAME).is(filename).andOperator(
                        Criteria.where(SURNAME).in("", "?"),
                        Criteria.where(SURNAME).exists(false)));
        return mongoTemplate.find(emptyQuery, PersonDocumentMongo.class);
    }

    /**
     * Performs query surname begins with.
     *
     * @param filename the filename of the dataset to search
     * @param beginsWith beginning substring
     * @return the list of matches
     */
    private List<PersonDocumentMongo> querySurnameBeginsWith(
            final String filename, final String beginsWith) {
        final Query searchQuery = new Query(Criteria.where(SURNAME)
                .regex("^" + beginsWith + ".*").and(FILENAME)
                .is(filename));
        return mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
    }

    /**
     * Finds the by root and surname begins with.
     *
     * @param rootDocument the root document
     * @param beginsWith the begins with
     * @return the resulting collection
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurnameBeginsWith(
            final RootDocument rootDocument, final String beginsWith) {
        log.debug("Starting findByRootAndSurnameBeginsWith");
        if (rootDocument == null) {
            return List.of();
        }
        final Collection<PersonDocument> personDocuments =
                findByFileAndSurnameBeginsWith(
                        rootDocument.getFilename(), beginsWith);
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        log.debug("Ending findByRootAndSurnameBeginsWith");
        return personDocuments;
    }

    /**
     * the list of person documents to create and associate.
     *
     * @param personDocuments
     */
    private void createGedObjects(
            final List<PersonDocumentMongo> personDocuments) {
        for (final PersonDocumentMongo personDocument : personDocuments) {
            final Person person = (Person) toObjConverter.createGedObject(
                    null, personDocument);
            personDocument.setGedObject(person);
        }
    }

    /**
     * Correct the type of the collection.
     *
     * @param in input collection
     * @return output collection
     */
    private Collection<PersonDocument> copy(
            final Collection<PersonDocumentMongo> in) {
        return in.stream()
            .sorted(new PersonDocumentComparator())
            .map(PersonDocument.class::cast)
            .toList();
    }

    /**
     * Standard sorting of Persons.
     *
     * @author Richard Schoeller
     */
    @NoArgsConstructor
    private static final class PersonDocumentComparator implements
            Comparator<PersonDocument>, Serializable {
        /**
         * The serial version u i d value.
         */
        private static final long serialVersionUID = 3;

        /**
         * Executes compare.
         *
         * @param arg0 the arg0
         * @return the resulting int
         */
        @Override
        public int compare(final PersonDocument arg0,
                final PersonDocument arg1) {
            final PersonComparator pc = new PersonComparator();
            return pc.compare(arg0.getGedObject(), arg1.getGedObject());
        }
    }

    /**
     * Finds the all.
     *
     * @param filename the filename to use
     * @return the resulting iterable
     */
    @Override
    public Iterable<PersonDocument> findAll(final String filename) {
        final Query searchQuery = new Query(
                Criteria.where(FILENAME).is(filename));
        final List<PersonDocumentMongo> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocumentMongo.class);
        createGedObjects(personDocuments);
        return copy(personDocuments);
    }

    /**
     * Finds the all.
     *
     * @param rootDocument the root document
     * @return the resulting iterable
     */
    @Override
    public Iterable<PersonDocument> findAll(final RootDocument rootDocument) {
        if (rootDocument == null) {
            return List.of();
        }
        final Iterable<PersonDocument> personDocuments =
                findAll(rootDocument.getFilename());
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        return personDocuments;
    }

    /**
     * Executes count.
     *
     * @param filename the filename to use
     * @return the resulting long
     */
    @Override
    public long count(final String filename) {
        final Query searchQuery =
                new Query(Criteria.where(FILENAME).is(filename));
        return mongoTemplate.count(searchQuery, PersonDocumentMongo.class);
    }

    /**
     * Returns the long.
     *
     * @param rootDocument the root document
     * @return the resulting long
     */
    @Override
    public long count(final RootDocument rootDocument) {
        return count(rootDocument.getFilename());
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public String lastId(final RootDocument rootDocument) {
        return lastId(mongoTemplate, PersonDocumentMongo.class,
                rootDocument.getFilename(), "I");
    }

    /**
     * Returns the string.
     *
     * @param rootDocument the root document
     * @return the resulting string
     */
    @Override
    public String newId(final RootDocument rootDocument) {
        return newId(mongoTemplate, PersonDocumentMongo.class,
                rootDocument.getFilename(), "I");
    }
}
