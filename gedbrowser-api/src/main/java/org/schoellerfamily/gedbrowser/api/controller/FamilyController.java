package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class FamilyController extends Fetcher<FamilyDocument> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of persons
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/families")
    @ResponseBody
    public List<ApiFamily> families(
            @PathVariable final String db) {
        logger.info("Entering read /dbs/" + db + "/families");
        final List<ApiFamily> list = new ArrayList<>();
        for (final FamilyDocument family : fetch(db, Family.class)) {
            list.add(d2dm.convert(family));
        }
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily family(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/families/" + id);
        return d2dm.convert(fetch(db, id, Family.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attributes of the person
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/families/" + id
                + "/attributes");
        return d2dm.convert(fetch(db, id, Family.class)).getAttributes();
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/families/" + id
                + "/attributes/" + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Family.class)).getAttributes();
        if (index >= attributes.size()) {
            throw new ObjectNotFoundException(
                    "Attribute " + index + "of family " + id + " not found",
                    "attribute", "id/attributes/" + index, db);
        }
        return attributes.get(index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/{type}")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/families/" + id + "/"
                + type);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Family.class)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/families/" + id + "/"
                + type + "/" + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Family.class)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        if (index >= list.size()) {
            throw new ObjectNotFoundException(
                    type + " " + index + " of person " + id + " not found",
                    "attribute", id + "/attributes/" + type + "/" + index, db);
        }
        return list.get(index);
    }
}
