package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = { "http://largo.schoellerfamily.org:4200", "http:/localhost:4200" } )
@Controller
public class SubmitterController
    extends OperationsEnabler<Submitter, SubmitterDocument>
    implements CrudOperations<Submitter, SubmitterDocument, ApiSubmitter> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Submitter> getGedClass() {
        return Submitter.class;
    }

    /**
     * @param db the name of the db to access
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    @PostMapping(value = "/dbs/{db}/submitters")
    @ResponseBody
    public ApiObject createSubmitter(@PathVariable final String db,
            @RequestBody final ApiSubmitter submitter) {
        logger.info("Entering create submitter in db: " + db);
        return create(readRoot(db), submitter, (i, id) ->
            new ApiSubmitter(i.getType(), id, i.getAttributes(), i.getName()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/submitters/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createSubmitterAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering submitter createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(read(db, id), index, attribute);
    }

    /**
     * @param db the name of the db to access
     * @return the list of submitters
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/submitters")
    @ResponseBody
    public List<ApiSubmitter> readSubmitters(
            @PathVariable final String db) {
        logger.info("Entering submitters, db: " + db);
        return convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter readSubmitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter, db: " + db + ", id: " + id);
        return convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the attributes of the submitter
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readSubmitterAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter attributes, db: " + db
                + ", id: " + id);
        return getD2dm().attributes(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readSubmitterAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering submitter attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return getD2dm().attribute(read(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readSubmitterAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/submitters/" + id + "/"
                + type);
        return getD2dm().attributes(read(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readSubmitterAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/submitters/" + id + "/"
                + type + "/" + index);
        return getD2dm().attribute(read(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submitter to update
     * @param submitter the data for the submitter
     * @return the submitter as created
     */
    @PutMapping(value = "/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiObject updateSubmitter(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmitter submitter) {
        logger.info("Entering update submitter in db: " + db);
        if (!id.equals(submitter.getString())) {
            return null;
        }
        return update(readRoot(db), submitter);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter deleteSubmitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        return delete(readRoot(db), id);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @param index the index of the attribute
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/submitters/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute deleteSubmitterAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        return deleteAttribute(readRoot(db), id, index);
    }
}
