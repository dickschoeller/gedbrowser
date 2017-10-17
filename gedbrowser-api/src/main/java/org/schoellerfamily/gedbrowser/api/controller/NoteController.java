package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiObject;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.persistence.domain.NoteDocument;
import org.springframework.stereotype.Controller;
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
public class NoteController
    extends OperationsEnabler<Note, NoteDocument>
    implements CreateOperations<Note, NoteDocument, ApiNote>,
        Fetcher<Note, NoteDocument, ApiNote> {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Note> getGedClass() {
        return Note.class;
    }

    /**
     * @param db the name of the db to access
     * @return the list of notes
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}/notes")
    @ResponseBody
    public List<ApiNote> readNotes(
            @PathVariable final String db) {
        logger.info("Entering notes, db: " + db);
        return getD2dm().convert(fetch(db));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the note
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/notes/{id}")
    @ResponseBody
    public ApiNote readNote(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering note, db: " + db + ", id: " + id);
        return getD2dm().convert(fetch(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @return the attributes of the note
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/notes/{id}/attributes")
    @ResponseBody
    public List<ApiAttribute> readNoteAttributes(
            @PathVariable final String db,
            @PathVariable final String id) {
        logger.info("Entering note attributes, db: " + db + ", id: " + id);
        return getD2dm().attributes(fetch(db, id));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @param index the index of the attribute
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/notes/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject readNoteAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index) {
        logger.info("Entering note attribute, db: " + db + ", id: " + id
                + ", index: " + index);
        return getD2dm().attribute(fetch(db, id), index);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @param type the type we are looking for
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/notes/{id}/{type}")
    @ResponseBody
    public List<ApiAttribute> readNoteAttributes(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type) {
        logger.info("Entering read /dbs/" + db + "/notes/" + id + "/"
                + type);
        return getD2dm().attributes(fetch(db, id), type);
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @param type the type we are looking for
     * @param index the index in the list of found matches
     * @return the attribute
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/dbs/{db}/notes/{id}/{type}/{index}")
    @ResponseBody
    public ApiObject readNoteAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final String type,
            @PathVariable final int index) {
        logger.info("Entering read /dbs/" + db + "/notes/" + id + "/"
                + type + "/" + index);
        return getD2dm().attribute(fetch(db, id), type, index);
    }

    /**
     * @param db the name of the db to access
     * @param note the data for the note
     * @return the note as created
     */
    @PostMapping(value = "/dbs/{db}/notes")
    @ResponseBody
    public ApiObject createNote(@PathVariable final String db,
            @RequestBody final ApiNote note) {
        logger.info("Entering create note in db: " + db);
        return create(fetchRoot(db), note, (i, id) ->
            new ApiNote(i.getType(), id, i.getAttributes(), i.getTail()));
    }

    /**
     * @param db the name of the db to access
     * @param id the ID of the note
     * @param index the index of the attribute
     * @param attribute the attribute value to add
     * @return the attribute
     */
    @PostMapping(value = "/dbs/{db}/notes/{id}/attributes/{index}")
    @ResponseBody
    public ApiObject createNoteAttribute(
            @PathVariable final String db,
            @PathVariable final String id,
            @PathVariable final int index,
            @RequestBody final ApiAttribute attribute) {
        logger.info("Entering note createAttribute,"
                + " db: " + db + ", id: " + id + ", index: " + index);
        return createAttribute(fetch(db, id), index, attribute);
    }
}
