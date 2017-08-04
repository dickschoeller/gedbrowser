package org.schoellerfamily.gedbrowser.renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;

/**
 * @author Dick Schoeller
 */
public final class IndexRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SourcesHrefRenderer<Root>,
        SubmittersHrefRenderer<Root> {
    /** Logger. */
    private final Log logger = LogFactory.getLog(getClass());

    /** */
    private final String base;

    /**
     * Constructor.
     *
     * @param root root of data set
     * @param base base string for page
     * @param renderingContext the context that we are rendering in
     */
    public IndexRenderer(final Root root, final String base,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
        this.base = base;
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
            final String liHtml = "<li id=\"" + person.getString() + "\">"
                    + html + "</li>";
            names.add(liHtml);
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
        final PersonVisitor visitor = new PersonVisitor();
        person.accept(visitor);
        if (visitor.isConfidential()) {
            return true;
        }
        if (getRenderingContext().isUser()) {
            return false;
        }
        final LivingEstimator le = new LivingEstimator(person,
                getRenderingContext());
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
            final String link = "<a id=\"letter-" + letter
                    + "\" href=\"surnames?db="
                    + getGedObject().getDbName() + "&amp;letter=" + letter
                    + "\" class=\"name\">" + "[" + letter + "]" + "</a>";
            indexLetters.add(link);
        }
        logger.info("Ending getLetters");
        return indexLetters;
    }

    /**
     * @return the href string to the same page were we are now
     */
    public String getIndexHref() {
        return "surnames?db=" + getGedObject().getDbName() + "&letter=" + base;
    }
}
