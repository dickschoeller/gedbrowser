package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmitter;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmitterDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class SubmitterController extends Fetcher<SubmitterDocument> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of submitters
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/submitters")
    @ResponseBody
    public List<ApiSubmitter> submitters(
            @PathVariable final String db) {
        logger.info("Entering submitters, db: " + db);
        return d2dm.convert(fetch(db, Submitter.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}")
    @ResponseBody
    public ApiSubmitter submitter(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter, db: " + db + ", id: " + id);
        return d2dm.convert(fetch(db, id, Submitter.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submitter
     * @return the attributes of the submitter
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submitters/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submitter attributes, db: " + db
                + ", id: " + id);
        return d2dm.attributes(fetch(db, id, Submitter.class));
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
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering submitter attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return d2dm.attribute(fetch(db, id, Submitter.class), index);
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
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/submitters/" + id + "/"
                + type);
        return d2dm.attributes(fetch(db, id, Submitter.class), type);
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
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/submitters/" + id + "/"
                + type + "/" + index);
        return d2dm.attribute(fetch(db, id, Submitter.class), type, index);
    }
}
