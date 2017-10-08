package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.persistence.domain.SourceDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class SourceController extends Fetcher<SourceDocument> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/sources")
    @ResponseBody
    public List<ApiSource> sources(
            @PathVariable final String db) {
        logger.info("Entering sources, db: " + db);
        final List<ApiSource> list = new ArrayList<>();
        for (final SourceDocument source : fetch(db, Source.class)) {
            list.add(d2dm.convert(source));
        }
        list.sort(new GetStringComparator());
        return list;
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the source
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}")
    @ResponseBody
    public ApiSource source(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source, db: " + db + ", id: " + id);
        return d2dm.convert(fetch(db, id, Source.class));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @return the attributes of the source
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/attributes")
    @ResponseBody
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering source attributes, db: " + db + ", id: " + id);
        return d2dm.convert(fetch(db, id, Source.class)).getAttributes();
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
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering source attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Source.class)).getAttributes();
        if (index >= attributes.size()) {
            throw new ObjectNotFoundException(
                    "Attribute " + index + "of source " + id + " not found",
                    "attribute", "id/attributes/" + index, db);
        }
        return attributes.get(index);
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
    public List<ApiObject> attributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/sources/" + id + "/"
                + type);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Source.class)).getAttributes();
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
     * @param id the ID of the source
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/sources/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject attribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/sources/" + id + "/"
                + type + "/" + index);
        final List<ApiObject> attributes =
                d2dm.convert(fetch(db, id, Source.class)).getAttributes();
        final List<ApiObject> list = new ArrayList<>();
        for (final ApiObject object : attributes) {
            if (object.isType(type)) {
                list.add(object);
            }
        }
        if (index >= list.size()) {
            throw new ObjectNotFoundException(
                    type + " " + index + " of source " + id + " not found",
                    "attribute", id + "/attributes/" + type + "/" + index, db);
        }
        return list.get(index);
    }
//
//    /**
//     * @param db the name of the db to access
//     * @param id the ID of the source
//     * @param index the index of the attribute
//     * @return the attribute
//     */
//    @RequestMapping(method = RequestMethod.POST,
//            value = "/dbs/{db}/sources/{id}/attributes/{index}")
//    @ResponseBody
//    public ApiObject createAttribute(
//            @PathVariable final String db,
//            @PathVariable final String id,
//            @PathVariable final int index) {
//        logger.info("Entering source createAttribute,"
//                + " db: " + db
//                + ", id: " + id
//                + ", index: " + index);
//        return null;
//    }
}
