package org.schoellerfamily.gedbrowser.api.controller;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.writer.GedWriterLine;
import org.schoellerfamily.gedbrowser.writer.creator.GedObjectToGedWriterVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Emits GEDCOM to the HTTP connection to download the GEDCOM state.
 *
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
public class SaveController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /**
     * Connects HTML template file with data for saving the GEDCOM file.
     *
     * @param db name of database for the lookup
     * @param response the servlet response object, needed for tweaking headers
     * @return a string identifying which HTML template to use.
     */
    @GetMapping(value = "/v1/dbs/{db}/save",
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public final ResponseEntity<String> save(@PathVariable final String db,
            final HttpServletResponse response) {
        logger.info("Starting save");
        // check here whether authorized.
        final Root root = fetchRoot(db);

        setHeaders(response, root);

        final String contents = renderGedFile(root);
        final String filename = root.getDbName() + ".ged";
        logger.info("filename: " + filename);
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlExposeHeaders(
                Collections.singletonList("Content-Disposition"));
        headers.set("Content-Disposition", "attachment; filename=" + filename);
        headers.setContentType(MediaType.TEXT_PLAIN);
        logger.info("Exiting save");
        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    protected final Root fetchRoot(final String dbName) {
        final Root root = loader.loadDocument(dbName).getGedObject();
        if (root == null) {
            throw new DataSetNotFoundException(
                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * @param root the dataset to render
     * @return the gedcom file as a string
     */
    private String renderGedFile(final Root root) {
        final GedObjectToGedWriterVisitor visitor =
                new GedObjectToGedWriterVisitor();
        root.accept(visitor);
        final StringBuilder builder = new StringBuilder();
        for (final GedWriterLine line : visitor.getLines()) {
            if (line.getLine().isEmpty()) {
                continue;
            }
            builder.append(line.getLine()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Fill in response headers to make this save the file instead of
     * displaying it.
     *
     * @param response the servlet response
     * @param root the root of the dataset being saved
     */
    private void setHeaders(final HttpServletResponse response,
            final Root root) {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition",
                "attachment; filename=" + getSaveFilename(root));
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
