package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiFamily;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.persistence.domain.FamilyDocument;
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
@CrossOrigin(origins = { "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
public final class FamilyController
    extends OperationsEnabler<Family, FamilyDocument>
    implements CrudOperations<Family, FamilyDocument, ApiFamily> {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Family> getGedClass() {
        return Family.class;
    }

    /**
     * @param db the name of the db to access
     * @param family the data for the family
     * @return the family as created
     */
    @PostMapping(value = "/dbs/{db}/families")
    @ResponseBody
    public ApiObject createFamily(@PathVariable final String db,
            @RequestBody final ApiFamily family) {
        logger.info("Entering create family in db: " + db);
        return create(readRoot(db), family, (i, id) ->
            new ApiFamily(i.getType(), id, i.getAttributes()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the source
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/families/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createFamilyAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering family createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(read(db, id), index, attribute);
    }

    /**
     * @param db the name of the db to access
     * @return the list of families
     */
    @GetMapping(value = "/dbs/{db}/families")
    @ResponseBody
    public List<ApiFamily> readFamilies(
            @PathVariable final String db) {
        logger.info("Entering read /dbs/" + db + "/families");
        return convert(read(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the family
     */
    @GetMapping(value = "/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily readFamily(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/families/" + id);
        return convert(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the attributes of the family
     */
    @GetMapping(value = "/dbs/{db}/families/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readFamilyAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering read /dbs/" + db + "/families/" + id
                + "/attributes");
        return getD2dm().attributes(read(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @param index the index of the attribute
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/families/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readFamilyAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/families/" + id
                + "/attributes/" + index);
        return getD2dm().attribute(read(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @param type the type we are looking for
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/families/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readFamilyAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/families/" + id + "/"
                + type);
        return getD2dm().attributes(read(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @GetMapping(value = "/dbs/{db}/families/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readFamilyAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/families/" + id + "/"
                + type + "/" + index);
        return getD2dm().attribute(read(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param id the id of the family to update
     * @param family the data for the family
     * @return the family as created
     */
    @PutMapping(value = "/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiObject updateFamily(@PathVariable final String db,
            @PathVariable final String id,
            @RequestBody final ApiFamily family) {
        logger.info("Entering update family in db: " + db);
        if (!id.equals(family.getString())) {
            return null;
        }
        return update(readRoot(db), family);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/families/{id}")
    @ResponseBody
    public ApiFamily deleteFamily(
            @PathVariable final String db,
            @PathVariable final String id) {
        return delete(readRoot(db), id);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the family
     * @param index the index of the attribute
     * @return the deleted object
     */
    @DeleteMapping(value = "/dbs/{db}/families/{id}/attributes/{index}")
    @ResponseBody
    public ApiAttribute deleteFamilyAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        return deleteAttribute(readRoot(db), id, index);
    }
}
