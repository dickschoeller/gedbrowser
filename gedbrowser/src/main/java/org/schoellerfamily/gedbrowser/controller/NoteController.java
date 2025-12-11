package org.schoellerfamily.gedbrowser.controller;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.controller.exception.NoteNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.GedRenderer;
import org.schoellerfamily.gedbrowser.renderer.GedRendererFactory;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.application.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dick Schoeller
 */
@Controller
@Slf4j
public class NoteController extends DatedDataController {

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Value("${gedbrowser.home}")
    private transient String gedbrowserHome;

    /**
     * Connects HTML template file with data for the source page.
     *
     * @param idString id URL argument.
     * @param dbName name of database for the lookup.
     * @param model Spring connection between the data model wrapper.
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping("/note")
    public final String note(
            @RequestParam(value = "id",
                required = false,
                defaultValue = "N0") final String idString,
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final Model model) {
        log.debug("Entering source");

        final Root root = fetchRoot(dbName);

        final RenderingContext context = createRenderingContext();
        final Note note = (Note) root.find(idString);
        if (note == null) {
            throw new NoteNotFoundException("Note %s not found".formatted(idString), idString, dbName, context);
        }

        final GedRenderer<?> noteRenderer = new GedRendererFactory()
                .create(note, context);

        model.addAttribute("filename", gedbrowserHome + "/" + dbName + ".ged");
        model.addAttribute("noteString", note.getString());
        model.addAttribute("model", noteRenderer);
        model.addAttribute("appInfo", appInfo);
        log.debug("Exiting source");
        return "note";
    }
}
