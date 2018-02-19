package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSubmission;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.persistence.domain.SubmissionDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = { "http://localhost:4200" })
@Controller
public class SubmissionController
    extends OperationsEnabler<Submission, SubmissionDocument>
    implements CrudOperations<Submission, SubmissionDocument, ApiSubmission> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Submission> getGedClass() {
        return Submission.class;
    }

    /**
     * @param db the name of the db to access
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PostMapping(value = "/dbs/{db}/submissions")
    @ResponseBody
    public ApiSubmission createSubmission(@PathVariable final String db,
            @RequestBody final ApiSubmission submission) {
        logger.info("Entering create submission in db: " + db);
        return create(readRoot(db), submission, (i, id) ->
            new ApiSubmission(i.getType(), id, i.getAttributes()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/submissions/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute createSubmissionAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering submission createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(read(db, id), index, attribute);
    }

    /**
     * @param db the name of the db to access
     * @return the list of submissions
     */
    @GetMapping(value = "/dbs/{db}/submissions")
    @ResponseBody
    public List<ApiSubmission> readSubmissions(
            @PathVariable final String db) {
        logger.info("Entering submissions, db: " + db);
        return getD2dm().convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the submission
     */
    @GetMapping(value = "/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission readSubmission(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submission, db: " + db + ", id: " + id);
        return getD2dm().convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the attributes of the submission
     */
    @GetMapping(value = "/dbs/{db}/submissions/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readSubmissionAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering submission attributes, db: " + db + ","
                + " id: " + id);
        return getD2dm().attributes(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param index the index of the attribute
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/submissions/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readSubmissionAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering submission attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return getD2dm().attribute(read(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param type the type we are looking for
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/submissions/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readSubmissionAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/submissions/" + id + "/"
                + type);
        return getD2dm().attributes(read(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/submissions/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readSubmissionAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/submissions/" + id + "/"
                + type + "/" + index);
        return getD2dm().attribute(read(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the submission to update
     * @param submission the data for the submission
     * @return the submission as created
     */
    @PutMapping(value = "/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiObject updateSubmission(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiSubmission submission) {
        logger.info("Entering update submission in db: " + db);
        if (!id.equals(submission.getString())) {
            return null;
        }
        return update(readRoot(db), submission);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/submissions/{id}")
    @ResponseBody
    public ApiSubmission deleteSubmission(
            @PathVariable final String db,
            @PathVariable final String id) {
        return delete(readRoot(db), id);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the submission
     * @param index the index of the attribute
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/submissions/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute deleteSubmissionAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        return deleteAttribute(readRoot(db), id, index);
    }
}
