package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.loader.GedObjectFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

/**
 * @author Dick Schoeller
 */
@Component
public class RestoreEndpoint implements Endpoint<List<String>> {

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getId() {
        return "restore";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<String> invoke() {
        final List<String> messages = new ArrayList<>();
        logger.info("Invoke restore");
        loader.reloadAll();
        messages.add("Reloaded " + loader.details().size() + " datasets");
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isSensitive() {
        return true;
    }
}
