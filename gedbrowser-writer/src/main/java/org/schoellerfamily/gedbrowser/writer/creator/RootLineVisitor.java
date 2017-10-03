package org.schoellerfamily.gedbrowser.writer.creator;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.schoellerfamily.gedbrowser.datamodel.Family;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Head;
import org.schoellerfamily.gedbrowser.datamodel.Note;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.datamodel.Source;
import org.schoellerfamily.gedbrowser.datamodel.Submission;
import org.schoellerfamily.gedbrowser.datamodel.Submitter;
import org.schoellerfamily.gedbrowser.datamodel.Trailer;
import org.schoellerfamily.gedbrowser.datamodel.util.GetStringComparator;
import org.schoellerfamily.gedbrowser.writer.GedWriterFile;

/**
 * @author Dick Schoeller
 */
public interface RootLineVisitor extends GedObjectLineVisitor {

    /**
     * {@inheritDoc}
     */
    @Override
    default void visit(final Root root) {
        final GedWriterFile file = new GedWriterFile(root);
        getLines().add(file);
        initLevel();
        processHead(root);
        processSubmitters(root);
        processPersons(root);
        processFamilies(root);
        processSources(root);
        processNotes(root);
        processSubmissions(root);
        processTrailers(root);
    }

    /**
     * @param root the root of the data set
     */
    default void processHead(final Root root) {
        for (final Head head : find(root, Head.class)) {
            head.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processSubmitters(final Root root) {
        final Collection<Submitter> submitters = find(root, Submitter.class);
        for (final Submitter submitter : submitters) {
            submitter.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processPersons(final Root root) {
        final Collection<Person> persons = find(root, Person.class);
        for (final Person person : persons) {
            person.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processFamilies(final Root root) {
        final Collection<Family> families = find(root, Family.class);
        for (final Family family : families) {
            family.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processSources(final Root root) {
        final Collection<Source> sources = find(root, Source.class);
        for (final Source source : sources) {
            source.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processNotes(final Root root) {
        final Collection<Note> notes = find(root, Note.class);
        for (final Note note : notes) {
            note.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processSubmissions(final Root root) {
        final Collection<Submission> submissions =
                find(root, Submission.class);
        for (final Submission submission : submissions) {
            submission.accept(this);
        }
    }

    /**
     * @param root the root of the data set
     */
    default void processTrailers(final Root root) {
        final Collection<Trailer> trailers = find(root, Trailer.class);
        for (final Trailer trailer : trailers) {
            trailer.accept(this);
        }
    }

    /**
     * Find all of the items of a particular type in this data set and sort by
     * ID.
     * @param root the root that identifies the data set
     * @param clazz the class
     * @param <T> the data type to be compared
     * @return the collection of matching items
     */
    default <T extends GedObject> Collection<T> find(final Root root,
            final Class<T> clazz) {
        final Collection<T> collection = root.find(clazz);
        final Set<T> set = new TreeSet<>(new GetStringComparator());
        set.addAll(collection);
        return set;
    }

}
