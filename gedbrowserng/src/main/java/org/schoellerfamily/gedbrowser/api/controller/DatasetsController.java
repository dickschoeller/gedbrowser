package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@Controller
@RequiredArgsConstructor
@Slf4j
public final class DatasetsController {
    /** */
    private final RepositoryManagerMongo repositoryManager;

    /**
     * Controller to get the currently loaded data sets.
     * @return a list of the names of the data sets
     */
    @GetMapping(value = "/v1/dbs")
    @ResponseBody
    public List<String> dbs() {
        log.info("Gettting list of DBs");
        final List<String> list = new ArrayList<>();
        for (final RootDocument mongo : ((RootDocumentRepositoryMongo) repositoryManager.get(Root.class)).findAll()) {
            list.add(mongo.getDbName());
        }
        log.info("length: {}", list.size());
        return list;
    }
}
