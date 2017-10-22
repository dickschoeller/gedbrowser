package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class SourceController
    extends OperationsEnabler<Source, SourceDocument>
    implements CreateOperations<Source, SourceDocument, ApiSource>,
        ReadOperations<Source, SourceDocument, ApiSource>,
        DeleteOperations<Source, SourceDocument, ApiSource> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Source> getGedClass() {
        return Source.class;
    }

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/sources")
    @ResponseBody
    public List<ApiSource> readSources(
            @PathVariable final String db) {
        logger.info("Entering sources, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the source
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource readSource(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the attributes of the source
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readSourceAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source attributes, db: " + db + ", id: " + id);
        return getD2dm().attributes(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readSourceAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering source attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return getD2dm().attribute(read(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readSourceAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/sources/" + id + "/"
                + type);
        return getD2dm().attributes(read(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readSourceAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/sources/" + id + "/"
                + type + "/" + index);
        return getD2dm().attribute(read(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param source the data for the source
     * @return the source as created
     */
    @PostMapping(value = "/dbs/{db}/sources")
    @ResponseBody
    public ApiObject createSource(@PathVariable final String db,
            @RequestBody final ApiSource source) {
        logger.info("Entering create source in db: " + db);
        return create(readRoot(db), source, (i, id) ->
            new ApiSource(i.getType(), id, i.getAttributes()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/sources/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createSourceAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering source createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(read(db, id), index, attribute);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource deleteSource(
            @PathVariable final String db,
            @PathVariable final String id) {
        return delete(readRoot(db), id);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/sources/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute deleteSourceAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        return deleteAttribute(readRoot(db), id, index);
    }
}
