package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
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
        final List<ApiSubmitter> list = new ArrayList<>();
        for (final SubmitterDocument submitter : fetch(db, Submitter.class)) {
            list.add(d2dm.convert(submitter));
        }
        return list;
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
        return d2dm.convert(fetch(db, id, Submitter.class)).getAttributes();
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
        final List<ApiObject> attributes = d2dm
                .convert(fetch(db, id, Submitter.class)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }
}
