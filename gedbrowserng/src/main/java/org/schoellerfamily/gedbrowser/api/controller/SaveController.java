package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.writer.GedWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Emits GEDCOM to the HTTP connection to download the GEDCOM state.
 *
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
@RequiredArgsConstructor
@Slf4j
public class SaveController {

    /** */
    private final GedDocumentFileLoader loader;

    private final RepositoryManagerMongo repositoryManager;

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
        log.info("Starting save");
        // check here whether authorized.
        try {
            final Root root = fetchRoot(db);

            final String contents = new GedWriter(root).writeString();
            final String filename = db + ".ged";
            log.info("filename: {}", filename);
            final HttpHeaders headers = new HttpHeaders();
            headers.setAccessControlExposeHeaders(List.of("Content-Disposition"));
            headers.set(
                    "Content-Disposition", "attachment; filename=" + filename);
            headers.setContentType(MediaType.TEXT_PLAIN);
            log.info("Exiting save");
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (DataSetNotFoundException e) {
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            log.info("Exiting save");
            return new ResponseEntity<>(
                    e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param dbName the name of the database
     * @return the root object
     */
    protected final Root fetchRoot(final String dbName) {
        final RootDocument rootDocument = loader.loadDocument(repositoryManager, dbName);
        if (rootDocument == null) {
            throw new DataSetNotFoundException("Data set %s not found".formatted(dbName), dbName);
        }
        return rootDocument.getGedObject();
    }
}
