package org.schoellerfamily.gedbrowser.datamodel;

import org.schoellerfamily.gedbrowser.datamodel.visitor.GedObjectVisitor;

/**
 * Model a complex GEDCOM name.
 *
 * @author Dick Schoeller
 */
public final class Name extends GedObject implements Nameable {
    /**
     * Holds the surname, which was parsed from the input string.
     */
    private String surname = "";
    /**
     * Holds any part of the name before the surname.
     */
    private String prefix = "";
    /**
     * Holds any part of the name after the surname.
     */
    private String suffix = "";

    /**
     * Creates a new Name.
     *
     */
    public Name() {
        super();
    }

    /**
     * Creates a new Name.
     *
     * @param parent the parent
     */
    public Name(final GedObject parent) {
        super(parent);
        init();
    }

    /**
     * Creates a new Name.
     *
     * @param parent the parent
     * @param string the string
     */
    public Name(final GedObject parent, final String string) {
        super(parent, string);
        init();
    }

    /**
     * Parse the name string from GEDCOM normal into its component parts.
     */
    public void init() {
        final String string = getString();
        final int bpos = string.indexOf('/');
        if (bpos == -1) {
            prefix = string;
            surname = "";
            suffix = "";
        } else {
            final int epos = string.indexOf('/', bpos + 1);
            prefix = string.substring(0, bpos);
            surname = string.substring(bpos + 1, epos);
            suffix = string.substring(epos + 1);
        }
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public Name getName() {
        return this;
    }

    /**
     * Gets the surname.
     *
     * @return the surname
     */
    @Override
    public String getSurname() {
        if (surname.isEmpty()) {
            return "?";
        } else {
            return surname;
        }
    }

    /**
     * Returns the prefix.
     *
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @return the suffix portion of the name. In western names this is usually
     *         a generation modifier.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Gets the index name.
     *
     * @return the index name
     */
    @Override
    public String getIndexName() {
        final boolean hasPrefix = !prefix.isEmpty();
        final boolean hasSuffix = !suffix.isEmpty();

        // Use standard method to get surname first.
        final StringBuilder buffer = new StringBuilder();
        if (surname.isEmpty()) {
            buffer.append('?');
        } else {
            buffer.append(getSurname());
        }

        // If prefix is present include it after ",".
        //
        // If no prefix or suffix are present , substitute "?" where prefix
        // would go.
        //
        // Suffix with no prefix is assumed to be Chinese style name with
        // surname first. No "," is inserted and the missing prefix is
        // not covered by ?.
        //
        if (hasPrefix) {
            buffer.append(", ").append(prefix);
        } else if (!hasSuffix) {
            buffer.append(", ?");
        }

        if (hasSuffix) {
            if (hasPrefix) {
                buffer.append(", ").append(suffix);
            } else {
                buffer.append(' ').append(suffix);
            }
        }

        return buffer.toString();
    }

    /**
     * Executes accept.
     *
     * @param visitor the visitor
     */
    @Override
    public void accept(final GedObjectVisitor visitor) {
        visitor.visit(this);
    }
}
