package org.schoellerfamily.gedbrowser.api.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = {
        "http://largo.schoellerfamily.org:4200", "http://localhost:4200" })
@RestController
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
    public List<String> dbs() {
        log.info("Gettting list of DBs");
        final Iterable<RootDocumentMongo> all =
            ((RootDocumentRepositoryMongo) repositoryManager.get(Root.class)).findAll();
        final List<String> list =
            StreamSupport.stream(all.spliterator(), false)
                .map(m -> m.getDbName())
                .toList();
        log.info("length: {}", list.size());
        return list;
    }
}
