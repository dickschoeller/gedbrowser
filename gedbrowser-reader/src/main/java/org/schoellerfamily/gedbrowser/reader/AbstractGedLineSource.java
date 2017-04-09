package org.schoellerfamily.gedbrowser.reader;

/**
 * @author Dick Schoeller
 */
public abstract class AbstractGedLineSource implements GedLineSource {
    /**
     * Current line number.
     */
    private int maxLineNumber;

    /**
     * @return the highest line number known in the file.
     */
    public final int getMaxLineNumber() {
        return maxLineNumber;
    }

    /**
     * @return the next line number in the file.
     */
    public final int nextLineNumber() {
        return maxLineNumber++;
    }

    /**
     * {@inheritDoc}
     */
    public final AbstractGedLine createGedLine(final AbstractGedLine parent,
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
            processLevel0(line, gedLine);
        } else {
            process(line, gedLine);
        }
        return gedLine;
    }

    /**
     * @param lineIn the input line
     * @param gedLine the AbstractGedLine being built
     */
    private void process(final String lineIn, final AbstractGedLine gedLine) {
        gedLine.setXref("");

        final String[] parts = lineIn.trim().split("[ \t]", 2);
        gedLine.setTag(parts[0]);
        if (parts.length == 1) {
            gedLine.setTail("");
        } else {
            gedLine.setTail(parts[1].trim());
        }
    }

    /**
     * @param lineIn the input line
     * @param gedLine the AbstractGedLine being built
     */
    private void processLevel0(final String lineIn,
            final AbstractGedLine gedLine) {
        String line = lineIn;
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
    }
}
