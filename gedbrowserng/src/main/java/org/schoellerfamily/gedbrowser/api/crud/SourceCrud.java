package org.schoellerfamily.gedbrowser.api.crud;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

/**
 * @author Dick Schoeller
 */
public class SourceCrud
    extends OperationsEnabler<Source, SourceDocument>
    implements CrudOperations<Source, SourceDocument, ApiSource> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * @param loader the file loader that we will use
     * @param toDocConverter the document converter
     * @param repositoryManager the repository manager
     */
    public SourceCrud(final GedDocumentFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FindableDocument<Source, SourceDocument> getRepository() {
        return getRepositoryManager().getSourceDocumentRepository();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Source> getGedClass() {
        return Source.class;
    }

    /**
     * @param db the name of the db to access
     * @param source the data for the source
     * @return the source as created
     */
    public ApiObject createSource(final String db,
            final ApiSource source) {
        logger.info("Entering create source in db: " + db);
        return create(readRoot(db), source, (i, id) -> new ApiSource(i, id));
    }

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    public List<ApiSource> readSources(
            final String db) {
        logger.info("Entering sources, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the source
     */
    public ApiSource readSource(
            final String db,
            final String id) {
        logger.info("Entering source, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the source to update
     * @param source the data for the source
     * @return the source as created
     */
    public ApiObject updateSource(final String db,
            final String id,
            final ApiSource source) {
        logger.info("Entering update source in db: " + db);
        if (!id.equals(source.getString())) {
            return null;
        }
        return update(readRoot(db), source);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the deleted object
     */
    public ApiSource deleteSource(
            final String db,
            final String id) {
        unlinkSourceFromPersons(db, id);
        unlinkSourceFromFamilies(db, id);
        return delete(readRoot(db), id);
    }

    /**
     * @param db the dataset we are working on
     * @param id the ID of the source to unlink
     */
    private void unlinkSourceFromPersons(final String db, final String id) {
        final List<ApiPerson> persons = personCrud().readPersons(db);
        for (final ApiPerson person : persons) {
            if (unlinkSource(person, id)) {
                personCrud().updatePerson(db, person.getString(), person);
            }
        }
    }

    /**
     * @param db the dataset we are working on
     * @param id the ID of the source to unlink
     */
    private void unlinkSourceFromFamilies(final String db, final String id) {
        final List<ApiFamily> families = familyCrud().readFamilies(db);
        for (final ApiFamily family : families) {
            if (unlinkSource(family, id)) {
                familyCrud().updateFamily(db, family.getString(), family);
            }
        }
    }

    /**
     * @return a new person CRUD object
     */
    private PersonCrud personCrud() {
        return new PersonCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * @return a new family CRUD object
     */
    private FamilyCrud familyCrud() {
        return new FamilyCrud(getLoader(), getConverter(),
                getRepositoryManager());
    }

    /**
     * Recursive method to drill down into the attributes of an API object and
     * remove links to the identified sources.
     *
     * @param object the object we are examining
     * @param id the id of the source to unlink
     * @return true if something under here was unlinked
     */
    private boolean unlinkSource(final ApiObject object, final String id) {
        boolean modified = false;
        final Iterator<ApiAttribute> i = object.getAttributes().iterator();
        while (i.hasNext()) {
            final ApiAttribute attribute = i.next();
            if (isTheSourceLinkWeAreLookingFor(attribute, id)) {
                i.remove();
                modified = true;
            } else if (unlinkSource(attribute, id)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Check if this is a link to the source we are unlinking.
     *
     * @param attribute the attribute to examine
     * @param id the id to check against
     * @return true if matches
     */
    private boolean isTheSourceLinkWeAreLookingFor(final ApiAttribute attribute,
            final String id) {
        return attribute.getType().equals("sourcelink")
                && attribute.getString().equals(id);
    }
}
