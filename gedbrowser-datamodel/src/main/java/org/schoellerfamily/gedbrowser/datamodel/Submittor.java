package org.schoellerfamily.gedbrowser.datamodel;

/**
 * @author Dick Schoeller
 */
public final class Submittor extends AbstractSource implements Nameable {
    /**
     * @param parent parent object of this submittor
     */
    public Submittor(final GedObject parent) {
        super(parent);
    }

    /**
     * @param parent parent object of this submittor
     * @param string long version of type string
     */
    public Submittor(final GedObject parent, final String string) {
        super(parent, string);
    }

    /**
     * @param parent parent object of this submittor
     * @param tag long version of type string
     * @param tail additional text to append to the string
     */
    public Submittor(final GedObject parent, final String tag,
            final String tail) {
        super(parent, processTail(tag, tail));
    }

    /**
     * Handle the concatenation. Separate with a space if both are not empty.
     *
     * @param tag long version of the type string
     * @param tail additional text to append to the string.
     * @return the tag and tail concatenated.
     */
    private static String processTail(final String tag, final String tail) {
        if (isEmpty(tag)) {
            if (isEmpty(tail)) {
                return "";
            } else {
                return tail;
            }
        } else {
            if (isEmpty(tail)) {
                return tag;
            } else {
                return tag + " " + tail;
            }
        }
    }

    /**
     * @param string
     *            string to test for emptiness. Null is supported
     * @return true if the string is either null or empty
     */
    // TODO could move this into a utility class or into GedObject as a
    // protected method
    private static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Name getName() {
        return getNameAttribute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSurname() {
        return getName().getSurname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIndexName() {
        final Name name = getName();
        return name.getIndexName();
    }
}
