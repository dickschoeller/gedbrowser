package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.controller.ApplicationInfo;
import org.schoellerfamily.gedbrowser.datamodel.FinderStrategy;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.GedDocumentMongoFactory;
import org.schoellerfamily.gedbrowser.persistence.mongo.domain.RootDocumentMongo;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RootDocumentRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Add our own information to the health indicator.
 *
 * @author Dick Schoeller
 */
@Component
public class ApplicationHealthIndicator implements HealthIndicator {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo appInfo;

    /** */
    @Autowired
    private transient RootDocumentRepositoryMongo rootDocumentRepository;

    /** */
    @Autowired
    private transient FinderStrategy finder;

    /**
     * {@inheritDoc}
     */
    @Override
    public Health health() {
        logger.info("Add to health report");
        final Builder builder = Health.up();
        builder.withDetail("version", appInfo.getVersion());
        // TODO this shouldn't be bound to mongo. We need to bury that.
        final List<Map<String, Object>> datasetList = new ArrayList<>();
        for (final RootDocumentMongo mongo
                : rootDocumentRepository.findAll()) {
            logger.info("add " + mongo.getDbName()
                + " to datasets in health report");
            datasetList.add(createDetail(mongo));
        }
        builder.withDetail("datasets", datasetList);
        builder.up();
        return builder.build();
    }

    /**
     * @param mongo the current database
     * @return the details
     */
    private Map<String, Object> createDetail(final RootDocumentMongo mongo) {
        final Root root = (Root) GedDocumentMongoFactory.getInstance().
                createRoot(mongo, finder);
        mongo.setGedObject(root);
        final Map<String, Object> detailsMap = new HashMap<>();
        detailsMap.put("dbname", mongo.getDbName());
        detailsMap.put("filename", mongo.getFilename());
        return detailsMap;
    }
}
