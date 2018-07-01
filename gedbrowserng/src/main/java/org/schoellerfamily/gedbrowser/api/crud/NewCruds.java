package org.schoellerfamily.gedbrowser.api.crud;

import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;

/**
 * Interface provides default behaviors of creating CRUD processors.
 *
 * @author Dick Schoeller
 */
public interface NewCruds {
    /**
     * @return the associated file loader
     */
    GedDocumentFileLoader getLoader();

    /**
     * @return the associated document converter
     */
    GedObjectToGedDocumentMongoConverter getConverter();

    /**
     * @return the associated repository manager
     */
    RepositoryManagerMongo getRepositoryManager();

    /**
     * @return a new family CRUD object
     */
    default FamilyCrud familyCrud() {
        return new FamilyCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new head CRUD object
     */
    default HeadCrud headCrud() {
        return new HeadCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new note CRUD object
     */
    default NoteCrud noteCrud() {
        return new NoteCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new person CRUD object
     */
    default PersonCrud personCrud() {
        return new PersonCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new source CRUD object
     */
    default SourceCrud sourceCrud() {
        return new SourceCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new submission CRUD object
     */
    default SubmissionCrud submissionCrud() {
        return new SubmissionCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new submitter CRUD object
     */
    default SubmitterCrud submitterCrud() {
        return new SubmitterCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

}
