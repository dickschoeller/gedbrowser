package org.schoellerfamily.gedbrowser.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.transformers.DocumentToApiModelTransformer;
import org.schoellerfamily.gedbrowser.persistence.domain.HeadDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Dick Schoeller
 */
@Controller
public class HeadController extends Fetcher {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

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
}
