package org.schoellerfamily.gedobject.datamodel.factory;

import java.util.Map;
import java.util.TreeMap;

import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.ObjectId;

import lombok.NoArgsConstructor;



/**
 * Creates abstract ged object instances.
 *
 * @author Richard Schoeller
 */
@NoArgsConstructor
public abstract class AbstractGedObjectFactory implements GedObjectFactory {
    /** */
    protected static final Map<String, GedToken> TOKENS = new TreeMap<>();

    /**
     * Factory method creates the appropriate GedObject from the provided strings.
     *
     * @param parent the parent GedObject
     * @param xref   an optional ID string
     * @param tag    the GEDCOM tag
     * @param tail   the rest of the line
     * @return the GedObject
     */
    public final GedObject create(final GedObject parent, final String xref, final String tag,
        final String tail) {
        return getFactory(tag).create(parent, new ObjectId(xref), fullstring(tag), tail);
    }

    /**
     * Find the right factory for this tag. If not found, return the attribute factory.
     *
     * @param tag the tag
     * @return the factory
     */
    protected GedObjectFactory getFactory(final String tag) {
        return getToken(tag).getFactory();
    }

    private GedToken getToken(final String tag) {
        if (tag == null) {
            return TOKENS.get("RAW");
        }
        return TOKENS
            .computeIfAbsent(tag, k -> new GedToken(k, TOKENS.get("RAW").getFactory()));
    }

    /**
     * Get the full string for this tag. If not found, return the tag.
     *
     * @param tag the tag.
     * @return the full string.
     */
    public final String fullstring(final String tag) {
        String fullstring = getToken(tag).getFullString();
        if (fullstring == null) {
            fullstring = tag;
        }
        return fullstring;
    }

    /**
     * Put a token into the map.
     *
     * @param shortstring the short string
     * @param fullstring  the full string
     * @param factory     the factory
     */
    protected static void put(final String shortstring, final String fullstring,
            final GedObjectFactory factory) {
        TOKENS.put(shortstring, new GedToken(fullstring, factory));
    }
}
