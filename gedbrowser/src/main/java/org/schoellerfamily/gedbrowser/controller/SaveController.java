package org.schoellerfamily.gedbrowser.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.creator.GedWriterLineCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Emits GEDCOM to the HTTP connection to download the GEDCOM state.
 *
 * @author Dick Schoeller
 */
@Controller
public class SaveController extends AbstractController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * Connects HTML template file with data for saving the GEDCOM file.
     *
     * @param dbName name of database for the lookup
     * @param response the servlet response object, needed for tweaking headers
     * @return a string identifying which HTML template to use.
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public final String save(
            @RequestParam(value = "db",
                required = false,
                defaultValue = "schoeller") final String dbName,
            final HttpServletResponse response) {
        logger.debug("Entering save");

        final RenderingContext context = createRenderingContext();
        if (!context.isAdmin() && !context.isUser()) {
            throw new ObjectNotFoundException("Can't find save", "ROOT", dbName,
                    context);
        }

        final Root root = fetchRoot(dbName);

        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition",
                "attachment; filename=" + getSaveFilename(root));

        final GedWriterLineCreator gedLineCreator = new GedWriterLineCreator();
        root.accept(gedLineCreator);
        final StringBuilder builder = new StringBuilder();
        for (final GedWriterLine line : gedLineCreator.getLines()) {
            if (line.getLine().isEmpty()) {
                continue;
            }
            builder.append(line.getLine()).append("\n");
        }
        logger.debug("Exiting save");
        return builder.toString();
    }

    /**
     * @param root the root object we are saving
     * @return the href string with the base filename.
     */
    private String getSaveFilename(final Root root) {
        final String filename = root.getFilename();
        final int lastIndexOf = filename.lastIndexOf("/");
        if (lastIndexOf == -1) {
            return filename;
        }
        return filename.substring(lastIndexOf + 1);
    }
}
