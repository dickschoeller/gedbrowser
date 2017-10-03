package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.persistence.domain.GedDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.schoellerfamily.gedbrowser.persistence.domain.RootDocument;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.schoellerfamily.gedbrowser.persistence.repository.FindableDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class HeadController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /**
     * Handles data conversion from DB model to API model.
     */
    private final DocumentToApiModelTransformer d2dm =
            new DocumentToApiModelTransformer();

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    @RequestMapping(method = RequestMethod.GET, value = "/dbs/{db}")
    @ResponseBody
    public ApiHead head(
            @PathVariable final String db) {
        logger.info("Entering head, db: " + db);
        final List<ApiHead> list = new ArrayList<>();
        for (final HeadDocument head : fetchHeads(db)) {
            list.add(d2dm.convert(head));
        }
        return list.get(0);
    }

    /**
     * @param dbName the name of the database
     * @return the list of sources
     */
    private List<HeadDocument> fetchHeads(final String dbName) {
        final RootDocument root = fetchRoot(dbName);
        return find(root);
    }

    /**
     * @param dbName the name of the database
     * @return the root object of the data set
     */
    private RootDocument fetchRoot(final String dbName) {
        final RootDocument root = loader.loadDocument(dbName);
        if (root == null) {
            logger.debug("Data set not found: " + dbName);
//            throw new DataSetNotFoundException(
//                    "Data set " + dbName + " not found", dbName);
        }
        return root;
    }

    /**
     * @param root the root document of the data set
     * @return the list of heads
     */
    private List<HeadDocument> find(final RootDocument root) {
        final FindableDocument<? extends GedObject, ? extends GedDocument<?>>
            repo = repositoryManager.get(
                    org.schoellerfamily.gedbrowser.datamodel.Head.class);
        final List<HeadDocument> all = new ArrayList<>();
        for (final GedDocument<?> document : repo.findAll(root)) {
            all.add((HeadDocument) document);
        }
        return all;
    }
}
