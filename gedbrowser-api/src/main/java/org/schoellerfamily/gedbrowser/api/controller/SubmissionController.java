package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class SubmissionController extends Fetcher<SubmissionDocument> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/submissions")
    @ResponseBody
    public List<ApiSubmission> submissions(
            @PathVariable final String db) {
        logger.info("Entering submissions, db: " + db);
        return d2dm.convert(fetch(db, Submission.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission submission(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submission, db: " + db + ", id: " + id);
        return d2dm.convert(fetch(db, id, Submission.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the attributes of the submission
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submission attributes, db: " + db + ","
                + " id: " + id);
        return d2dm.attributes(fetch(db, id, Submission.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering submission attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return d2dm.attribute(fetch(db, id, Submission.class), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/{type}")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/submissions/" + id + "/"
                + type);
        return d2dm.attributes(fetch(db, id, Submission.class), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/submissions/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/submissions/" + id + "/"
                + type + "/" + index);
        return d2dm.attribute(fetch(db, id, Submission.class), type, index);
    }
}
