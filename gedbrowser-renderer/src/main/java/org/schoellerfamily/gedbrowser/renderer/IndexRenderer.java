package org.schoellerfamily.gedbrowser.renderer;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.analytics.LivingEstimator;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.visitor.PersonVisitor;
import org.schoellerfamily.gedbrowser.renderer.href.HeaderHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.IndexHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.PlacesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SaveHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SourcesHrefRenderer;
import org.schoellerfamily.gedbrowser.renderer.href.SubmittersHrefRenderer;



/**
 * Renders index output for display.
 *
 * @author Richard Schoeller
 */
@Slf4j
public final class IndexRenderer extends GedRenderer<Root>
        implements HeaderHrefRenderer<Root>, IndexHrefRenderer<Root>,
        PlacesHrefRenderer<Root>, SaveHrefRenderer<Root>,
        SourcesHrefRenderer<Root>, SubmittersHrefRenderer<Root> {

    /** */
    private final String base;

    /**
     * Creates a new IndexRenderer.
     *
     * @param root the root
     * @param base the base
     * @param renderingContext the rendering context
     */
    public IndexRenderer(final Root root, final String base,
            final RenderingContext renderingContext) {
        super(root, new GedRendererFactory(), renderingContext);
        this.base = base;
    }

    /**
     * Gets the surnames.
     *
     * @return the surnames
     */
    public Collection<String> getSurnames() {
        return getGedObject().findBySurnamesBeginWith(base);
    }

    /**
     * Gets the index name htmls.
     *
     * @param surname the surname to use
     * @return the index name htmls
     */
    public Collection<String> getIndexNameHtmls(final String surname) {
        log.info("Starting getIndexNameHtmls");
        final Collection<Person> persons = getGedObject()
                .findBySurname(surname);
        final List<String> names = persons.stream()
                .filter(person -> !isHidden(person))
                .map(person -> {
                    final String html = createGedRenderer(person).getIndexName();
                    return "<li id=\"" + person.getString() + "\">"
                            + html
                            + indicateDeadWithoutRecord(person)
                            + "</li>";
                })
                .toList();
        log.info("Ending getIndexNameHtmls");
        return names;
    }

    private String indicateDeadWithoutRecord(final Person person) {
        final LivingEstimator le = new LivingEstimator(person,
                getRenderingContext());
        if (!le.estimate() && !le.hasDeathAttribute()) {
            return " <b>no death record</b>";
        }
        return "";
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
        final LivingEstimator le = new LivingEstimator(person, getRenderingContext());
        return le.estimate();
    }

    /**
     * Gets the base.
     *
     * @return the base
     */
    public String getBase() {
        return base;
    }

    /**
     * Returns the letters.
     *
     * @return the letters
     */
    public Collection<String> getLetters() {
        log.info("Starting getLetters");
        final List<String> indexLetters = getGedObject()
            .findSurnameInitialLetters().stream()
            .map(letter -> "<a id=\"letter-" + letter
                + "\" href=\"surnames?db="
                + getGedObject().getDbName() + "&amp;letter=" + letter
                + "\" class=\"name\">" + "[" + letter + "]" + "</a>")
            .toList();
        log.info("Ending getLetters");
        return indexLetters;
    }

    /**
     * Gets the index href.
     *
     * @return the index href
     */
    @Override
    public String getIndexHref() {
        return "surnames?db=" + getGedObject().getDbName() + "&letter=" + base;
    }
}
