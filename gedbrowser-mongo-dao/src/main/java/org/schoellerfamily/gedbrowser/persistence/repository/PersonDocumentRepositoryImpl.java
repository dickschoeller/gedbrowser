package org.schoellerfamily.gedbrowser.persistence.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocumentFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * @author Dick Schoeller
 */
public final class PersonDocumentRepositoryImpl implements
        FindableByNameDocument<Person, PersonDocument> {
    /** Logger. */
    private static final Logger LOGGER = Logger
            .getLogger(PersonDocumentRepositoryImpl.class.getName());

    /** */
    @Autowired
    private transient MongoTemplate mongoTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDocument findByFileAndString(final String filename,
            final String string) {
        final Query searchQuery = new Query(Criteria.where("string").is(string)
                .and("filename").is(filename));
        final PersonDocument personDocument = mongoTemplate.findOne(
                searchQuery, PersonDocument.class);
        if (personDocument == null) {
            return null;
        }
        final Person person = (Person) GedDocumentFactory.getInstance()
                .createGedObject(null, personDocument);
        personDocument.setGedObject(person);
        return personDocument;
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurname(
            final String filename, final String surname) {
        if (filename == null || surname == null) {
            return Collections.emptyList();
        }
        final Query searchQuery =
                new Query(Criteria.where("surname").is(surname)
                        .and("filename").is(filename));
        final List<PersonDocument> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocument.class);
        createGedObjects(personDocuments);
        Collections.sort(personDocuments, new PersonDocumentComparator());
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurname(
            final RootDocument rootDocument, final String surname) {
        if (rootDocument == null || surname == null) {
            return Collections.emptyList();
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
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFileAndSurnameBeginsWith(
            final String filename, final String beginsWith) {
        if (filename == null) {
            return Collections.emptyList();
        }
        if (beginsWith == null || beginsWith.equals("")
                || beginsWith.equals("?")) {
            final Query emptyQuery = new Query(Criteria.where("surname")
                    .is("").and("filename").is(filename));
            final Query nullQuery = new Query(Criteria.where("surname")
                    .exists(false).and("filename").is(filename));
            final Query qMarkQuery = new Query(Criteria.where("surname")
                    .is("?").and("filename").is(filename));
            final List<PersonDocument> personDocuments =
                    mongoTemplate.find(emptyQuery, PersonDocument.class);
            personDocuments.addAll(mongoTemplate.find(nullQuery,
                    PersonDocument.class));
            personDocuments.addAll(mongoTemplate.find(qMarkQuery,
                    PersonDocument.class));
            createGedObjects(personDocuments);
            Collections.sort(personDocuments, new PersonDocumentComparator());
            return personDocuments;
        } else {
            final Query searchQuery = new Query(Criteria.where("surname")
                    .regex("^" + beginsWith + ".*").and("filename")
                    .is(filename));
            final List<PersonDocument> personDocuments =
                    mongoTemplate.find(searchQuery, PersonDocument.class);
            createGedObjects(personDocuments);
            Collections.sort(personDocuments, new PersonDocumentComparator());
            return personDocuments;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRootAndSurnameBeginsWith(
            final RootDocument rootDocument, final String beginsWith) {
        LOGGER.entering("PersonDocumentRepositoryImpl",
                "findByRootAndSurnameBeginsWith");
        if (rootDocument == null) {
            return Collections.emptyList();
        }
        final Collection<PersonDocument> personDocuments =
                findByFileAndSurnameBeginsWith(
                        rootDocument.getFilename(), beginsWith);
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        LOGGER.exiting("PersonDocumentRepositoryImpl",
                "findByRootAndSurnameBeginsWith");
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByFile(final String filename) {
        LOGGER.entering("PersonDocumentRepositoryImpl", "findByFile");
        final Query searchQuery = new Query(Criteria.where("filename").is(
                filename));
        final List<PersonDocument> personDocuments =
                mongoTemplate.find(searchQuery, PersonDocument.class);
        createGedObjects(personDocuments);
        Collections.sort(personDocuments, new PersonDocumentComparator());
        LOGGER.exiting("PersonDocumentRepositoryImpl", "findByFile");
        return personDocuments;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDocument> findByRoot(
            final RootDocument rootDocument) {
        LOGGER.entering("PersonDocumentRepositoryImpl", "findByRoot");
        if (rootDocument == null) {
            return Collections.emptyList();
        }
        final Collection<PersonDocument> personDocuments =
                findByFile(rootDocument.getFilename());
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = personDocument.getGedObject();
            person.setParent(rootDocument.getGedObject());
        }
        LOGGER.exiting("PersonDocumentRepositoryImpl", "findByRoot");
        return personDocuments;
    }

    /**
     * @param personDocuments
     *            the list of person documents to create and associate.
     */
    private void createGedObjects(final List<PersonDocument> personDocuments) {
        for (final PersonDocument personDocument : personDocuments) {
            final Person person = (Person) GedDocumentFactory.getInstance()
                    .createGedObject(null, personDocument);
            personDocument.setGedObject(person);
        }
    }

    /**
     * Standard sorting of Persons.
     *
     * @author Dick Schoeller
     */
    private static class PersonDocumentComparator implements
            Comparator<PersonDocument>, Serializable {
        // TODO combine with the person comparator.

        /** */
        private static final long serialVersionUID = 3;

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(final PersonDocument arg0,
                final PersonDocument arg1) {
            final Person p0 = arg0.getGedObject();
            final Person p1 = arg1.getGedObject();
            final int nameComparison =
                    p0.getIndexName().compareTo(p1.getIndexName());
            if (nameComparison != 0) {
                return nameComparison;
            }
            // If the names are the same, use the sort date (approximates on
            // birth).
            final int dateComparison =
                    p0.getSortDate().compareTo(p1.getSortDate());
            if (dateComparison != 0) {
                return dateComparison;
            }
            // If the dates are the same, use the I number.
            return p0.getString().compareTo(p1.getString());
        }
    }
}
