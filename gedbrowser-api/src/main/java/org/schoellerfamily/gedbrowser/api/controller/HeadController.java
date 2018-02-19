package org.schoellerfamily.gedbrowser.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@CrossOrigin(origins = { "http://localhost:4200" })
@Controller
public class HeadController
    extends OperationsEnabler<Head, HeadDocument>
    implements ReadOperations<Head, HeadDocument, ApiHead> {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Head> getGedClass() {
        return Head.class;
    }

    /**
     * @param db the name of the db to access
     * @return the list of sources
     */
    @GetMapping(value = "/dbs/{db}")
    @ResponseBody
    public ApiHead readHead(
            @PathVariable final String db) {
        logger.info("Entering head, db: " + db);
        return (ApiHead) getD2dm().convert(read(db)).get(0);
    }
}
