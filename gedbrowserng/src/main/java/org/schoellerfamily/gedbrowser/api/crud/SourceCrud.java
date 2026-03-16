package org.schoellerfamily.gedbrowser.api.crud;

import java.util.List;

import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.SourceDocumentRepositoryMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;

import lombok.extern.slf4j.Slf4j;



/**
 * Represents source crud.
 *
 * @author Richard Schoeller
 */
@Slf4j
public class SourceCrud
    extends OperationsEnabler<Source>
    implements CrudOperations<Source, SourceDocument, ApiSource>,
        ObjectCrud<ApiSource>,
        LinkCrud {

    /**
     * Creates a new SourceCrud.
     *
     * @param loader the loader
     * @param toDocConverter the to doc converter
     * @param repositoryManager the repository manager
     */
    public SourceCrud(final GedObjectFileLoader loader,
            final GedObjectToGedDocumentMongoConverter toDocConverter,
            final RepositoryManagerMongo repositoryManager) {
        super(loader, toDocConverter, repositoryManager);
    }

    /**
     * Returns the repository.
     *
     * @return the repository
     */
    @Override
    public FindableDocument<Source, SourceDocument> getRepository() {
        return ((SourceDocumentRepositoryMongo) getRepositoryManager().get(Source.class));
    }

    /**
     * Returns the ged class.
     *
     * @return the ged class
     */
    @Override
    public Class<Source> getGedClass() {
        return Source.class;
    }

    /**
     * Creates the one.
     *
     * @param db the db
     * @param source the source
     * @return the resulting api source
     */
    @Override
    public ApiSource createOne(final String db,
            final ApiSource source) {
        log.info("Entering create source in db: {}", db);
        return create(readRoot(getRepositoryManager(), db), source,
            (i, id) -> i.toBuilder().string(id).build());
    }

    /**
     * Executes read all.
     *
     * @param db the db
     * @return the resulting list
     */
    @Override
    public List<ApiSource> readAll(
            final String db) {
        log.info("Entering sources, db: {}", db);
        return getD2dm().convert(read(getRepositoryManager(), db));
    }

    /**
     * Executes read one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api source
     */
    @Override
    public ApiSource readOne(
            final String db,
            final String id) {
        log.info("Entering source, db: {}, id: {}", db, id);
        return getD2dm().convert(read(getRepositoryManager(), db, id));
    }

    /**
     * Executes update one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @param source the source
     * @return the resulting api source
     */
    @Override
    public ApiSource updateOne(final String db,
            final String id,
            final ApiSource source) {
        log.info("Entering update source in db: {}", db);
        if (!id.equals(source.getString())) {
            return null;
        }
        return update(readRoot(getRepositoryManager(), db), source);
    }

    /**
     * Executes delete one.
     *
     * @param db the db
     * @param id the unique identifier for the target
     * @return the resulting api source
     */
    @Override
    public ApiSource deleteOne(
            final String db,
            final String id) {
        unlinkFrom(db, id);
        return delete(readRoot(getRepositoryManager(), db), id);
    }

    /**
     * Indicates whether the link we are looking for.
     *
     * @param attribute the attribute
     * @param id the unique identifier for the target
     * @return true if the condition is met; otherwise false
     */
    @Override
    public boolean isTheLinkWeAreLookingFor(final ApiAttribute attribute,
            final String id) {
        return "sourcelink".equals(attribute.getType())
                && attribute.getString().equals(id);
    }
}
