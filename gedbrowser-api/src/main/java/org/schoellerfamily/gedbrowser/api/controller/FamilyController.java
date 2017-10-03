package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
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
public class FamilyController extends Fetcher {
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
        logger.info("Entering families, db: " + db);
        final List<ApiFamily> list = new ArrayList<>();
        for (final FamilyDocument family : fetchFamilies(db)) {
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
        logger.info("Entering family, db: " + db + ", id: " + id);
        return d2dm.convert(fetchFamily(db, id));
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
        logger.info("Entering family attributes, db: " + db + ", id: " + id);
        return d2dm.convert(fetchFamily(db, id)).getAttributes();
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
        logger.info("Entering family attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        if (index >= attributes.size()) {
            return null;
        }
        return attributes.get(index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/children")
    @ResponseBody
    public List<ApiObject> children(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering children, db: " + db + ", id: " + id);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> children = new ArrayList<>();
        for (final ApiObject attribute : attributes) {
            if ("child".equals(attribute.getType())) {
                children.add(attribute);
            }
        }
        return children;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the person
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/families/{id}/children/{index}")
    @ResponseBody
    public ApiObject child(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering child, db: " + db + ", id: " + id + ", index: "
                + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> children = new ArrayList<>();
        for (final ApiObject attribute : attributes) {
            if ("child".equals(attribute.getType())) {
                children.add(attribute);
            }
        }
        if (index >= children.size()) {
            return null;
        }
        return children.get(index);
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
        logger.info("Entering family attributes, db: " + db + ", id: " + id
                + ", index: " + type);
        final List<ApiObject> attributes =
                d2dm.convert(fetchFamily(db, id)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (isObjectDesiredType(type, object)) {
                list.add(object);
            }
        }
        return list;
    }

    /**
     * @param type the type we are looking for
     * @param object the object being checked
     * @return true if object satisfies
     */
    private boolean isObjectDesiredType(final String type,
            final ApiObject object) {
        final String objectType = object.getType();
        if (type.equals(objectType)) {
            return true;
        }
        return "attribute".equals(objectType)
                && type.equalsIgnoreCase(object.getString());
    }
}
