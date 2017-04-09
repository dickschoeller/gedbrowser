package org.schoellerfamily.gedbrowser.reader;

import java.io.IOException;

/**
 * @author Dick Schoeller
 */
public interface GedLineSource {
    /**
     * Create the next GedLine.
     *
     * @param parent the current parent.
     * @return the GedLine.
     * @throws IOException if this is a file source and there are problems.
     */
    AbstractGedLine createGedLine(AbstractGedLine parent) throws IOException;

    /**
     * @return the highest line number known in the file.
     */
    int getMaxLineNumber();

    /**
     * @return the next line number in the file.
     */
    int nextLineNumber();

    /**
     * Create a GedLine from the provided string.
     *
     * @param parent the current parent.
     * @param inLine the line of GEDCOM data
     * @return the GedLine
     */
    default AbstractGedLine createGedLine(final AbstractGedLine parent,
            final String inLine) {
        String line = inLine;
        if (line == null) {
            return null;
        }

        line = line.trim();

        if (line.isEmpty()) {
            return null;
        }

        final AbstractGedLine gedLine = new GedLine(parent);

        final String[] levelAndRest = line.split("[^0-9]", 2);
        gedLine.setLevel(Integer.parseInt(levelAndRest[0]));

        line = levelAndRest[1].trim();

        if (gedLine.getLevel() == 0) {
            int bpos = line.indexOf('@');
            if (bpos == -1) {
                gedLine.setXref("");
            } else {
                bpos++;
                final int epos = line.indexOf('@', bpos);
                gedLine.setXref(line.substring(bpos, epos));
                line = line.substring(epos + 1);
            }

            line = line.trim();
            final String[] parts = line.split("[ \t]", 2);
            gedLine.setTag(parts[0]);
            gedLine.setTail("");
        } else {
            gedLine.setXref("");

            line = line.trim();

            final String[] parts = line.split("[ \t]", 2);
            gedLine.setTag(parts[0]);
            if (parts.length == 1) {
                line = "";
            } else {
                line = parts[1].trim();
            }

            gedLine.setTail(line);
        }
        return gedLine;
    }
}
