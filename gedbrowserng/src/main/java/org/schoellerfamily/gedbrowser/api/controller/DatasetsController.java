package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
public final class DatasetsController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Controller to get the currently loaded data sets.
     * @return a list of the names of the data sets
     */
    @GetMapping(value = "/v1/dbs")
    @ResponseBody
    public List<String> dbs() {
        logger.info("Gettting list of DBs");
        final List<String> list = new ArrayList<>();
        for (final RootDocument mongo : repositoryManager
                .getRootDocumentRepository().findAll()) {
            list.add(mongo.getDbName());
        }
        logger.info("length: " + list.size());
        return list;
    }
}
