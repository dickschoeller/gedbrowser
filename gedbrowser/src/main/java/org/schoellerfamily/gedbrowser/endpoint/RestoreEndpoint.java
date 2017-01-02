package org.schoellerfamily.gedbrowser.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.loader.GedFileLoader;
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
    private transient GedFileLoader loader;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return "restore";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> invoke() {
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
    public boolean isEnabled() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSensitive() {
        return true;
    }
}
