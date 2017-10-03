package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.PersonDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dick Schoeller
 */
public class Fetcher {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    protected final RootDocument fetchRoot(final String dbName) {
        final RootDocument root = loader.loadDocument(dbName);
        if (root == null) {
            logger.debug("Data set not found: " + dbName);
//            throw new DataSetNotFoundException(
//                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * @param dbName the name of the database
     * @return the list of sources
     */
    protected List<HeadDocument> fetchHeads(final String dbName) {
        return find(fetchRoot(dbName), Head.class);
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the person
     * @return the person
     */
    protected PersonDocument fetchPerson(final String dbName,
            final String idString) {
        final PersonDocument person =
                find(fetchRoot(dbName), idString, Person.class);
        if (person == null) {
            logger.debug("Person not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return person;
    }

    /**
     * @param dbName the name of the database
     * @return the list of persons
     */
    protected List<PersonDocument> fetchPersons(final String dbName) {
        return find(fetchRoot(dbName), Person.class);
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the person
     * @return the person
     */
    protected FamilyDocument fetchFamily(final String dbName,
            final String idString) {
        final FamilyDocument family =
                find(fetchRoot(dbName), idString, Family.class);
        if (family == null) {
            logger.debug("Family not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return family;
    }

    /**
     * @param dbName the name of the database
     * @return the list of persons
     */
    protected List<FamilyDocument> fetchFamilies(final String dbName) {
        return find(fetchRoot(dbName), Family.class);
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the source
     * @return the source
     */
    protected SourceDocument fetchSource(final String dbName,
            final String idString) {
        final SourceDocument source =
                find(fetchRoot(dbName), idString, Source.class);
        if (source == null) {
            logger.debug("Source not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return source;
    }

    /**
     * @param dbName the name of the database
     * @return the list of sources
     */
    protected List<SourceDocument> fetchSources(final String dbName) {
        return find(fetchRoot(dbName), Source.class);
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the submission
     * @return the submission
     */
    protected SubmissionDocument fetchSubmission(final String dbName,
            final String idString) {
        final SubmissionDocument submission =
                find(fetchRoot(dbName), idString, Submission.class);
        if (submission == null) {
            logger.debug("Submission not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return submission;
    }

    /**
     * @param dbName the name of the database
     * @return the list of submissions
     */
    protected List<SubmissionDocument> fetchSubmissions(final String dbName) {
        return find(fetchRoot(dbName), Submission.class);
    }

    /**
     * @param dbName the name of the database
     * @param idString the ID of the submitter
     * @return the submitter
     */
    protected SubmitterDocument fetchSubmitter(final String dbName,
            final String idString) {
        final SubmitterDocument submitter =
                find(fetchRoot(dbName), idString, Submitter.class);
        if (submitter == null) {
            logger.debug("Submitter not found: " + idString);
//            throw new PersonNotFoundException(
//                    "Person " + idString + " not found", idString,
//                    root.getDbName(), context);
        }
        return submitter;
    }

    /**
     * @param dbName the name of the database
     * @return the list of submitters
     */
    protected List<SubmitterDocument> fetchSubmitters(final String dbName) {
        return find(fetchRoot(dbName), Submitter.class);
    }

    /**
     * @param root the root document of the data set to search
     * @param idString the ID of the family
     * @param clazz the class that we are searching for
     * @param <T> the type returned
     * @return the family document
     */
    @SuppressWarnings("unchecked")
    protected <T> T find(final RootDocument root,
            final String idString, final Class<? extends GedObject> clazz) {
        return (T) repositoryManager.get(clazz)
                .findByRootAndString(root, idString);
    }

    /**
     * @param root the root document of the data set
     * @param clazz the class being searched
     * @param <T> the type returned
     * @return the list of submitters
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> find(final RootDocument root,
            final Class<? extends GedObject> clazz) {
        final List<T> all = new ArrayList<>();
        for (final GedDocument<?> document
                : repositoryManager.get(clazz).findAll(root)) {
            all.add((T) document);
        }
        return all;
    }
}
