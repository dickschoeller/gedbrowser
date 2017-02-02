package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.analytics.CalendarProvider;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;

/**
 * @author Dick Schoeller
 */
public final class IndexRenderer extends GedRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    private final String base;
    /** Provides the "today" for use in comparisons. */
    private final CalendarProvider provider;

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param base base string for page
     * @param renderingContext the context that we are rendering in
     * @param provider the calendar provider we are using to determine now
     */
    public IndexRenderer(final Root root, final String base,
            final RenderingContext renderingContext,
            final CalendarProvider provider) {
        super(root, new GedRendererFactory(), renderingContext, provider);
        this.base = base;
        this.provider = provider;
    }

    /**
     * @return collection of surnames that match the base
     */
    public Collection<String> getSurnames() {
        return getGedObject().findBySurnamesBeginWith(base);
    }

    /**
     * @param surname surname that we will match on this
     * @return the collection of nameHtml results
     */
    public Collection<String> getIndexNameHtmls(final String surname) {
        logger.info("Starting getIndexNameHtmls");
        final Collection<Person> persons = getGedObject()
                .findBySurname(surname);
        final List<String> names = new ArrayList<>();
        for (final Person person : persons) {
            if (isHidden(person)) {
                continue;
            }
            final String html = createGedRenderer(person).getIndexName();
            names.add(html);
        }
        logger.info("Ending getIndexNameHtmls");
        return names;
    }

    /**
     * @param person the person we are checking
     * @return true if the person is confidential
     */
    private boolean isHidden(final Person person) {
        if (getRenderingContext().isAdmin()) {
            return false;
        }
        if (person.isConfidential()) {
            return true;
        }
        if (getRenderingContext().isUser()) {
            return false;
        }
        final LivingEstimator le = new LivingEstimator(person, provider);
        return le.estimate();
    }

    /**
     * @return the base letter of the index
     */
    public String getBase() {
        return base;
    }

    /**
     * @return the list of initial letters found in the surnames in the data
     *         base.
     */
    public Collection<String> getLetters() {
        logger.info("Starting getLetters");
        final List<String> indexLetters = new ArrayList<>();
        for (final String letter : getGedObject().findSurnameInitialLetters()) {
            final String link = "<a href=\"surnames?db="
                    + getGedObject().getDbName() + "&amp;letter=" + letter
                    + "\" class=\"name\">" + "[" + letter + "]" + "</a>";
            indexLetters.add(link);
        }
        logger.info("Ending getLetters");
        return indexLetters;
    }
}
