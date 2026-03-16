package org.schoellerfamily.gedbrowser.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.writer.creator.GedObjectToGedWriterVisitor;
import org.schoellerfamily.gedbrowser.writer.util.Backup;

/**
 * @author Richard Schoeller
 */
@Slf4j
public class GedWriter {

    private final GedObjectToGedWriterVisitor visitor =
            new GedObjectToGedWriterVisitor();

    /** */
    private final Root root;

    /**
     * Creates a new GedWriter.
     *
     * @param root the root
     */
    public GedWriter(final Root root) {
        this.root = root;
    }

    /**
     * Write the file as directed.
     */
    public void write() {
        root.accept(visitor);
        try {
            Backup.backup(root.getFilename());
        } catch (IOException e) {
            log.error("Problem backing up old copy of GEDCOM file", e);
        }
        final String filename = root.getFilename();
        final String charset = new CharsetScanner().charset(root);
        try (FileOutputStream fstream = new FileOutputStream(filename);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            writeTheLines(bstream, charset);
        } catch (IOException e) {
            log.error("Problem writing GEDCOM file", e);
        }
    }

    private void writeTheLines(final BufferedOutputStream stream,
            final String charset)
            throws IOException {
        for (final GedWriterLine line : visitor.getLines()) {
            if (line.getLine().isEmpty()) {
                continue;
            }
            final String string = line.getLine();
            stream.write(string.getBytes(charset));
            stream.write('\n');
        }
    }

    /**
     * Executes write string.
     *
     * @return the resulting string
     */
    public String writeString() {
        root.accept(visitor);
        final StringBuilder builder = new StringBuilder();
        for (final GedWriterLine line : visitor.getLines()) {
            if (line.getLine().isEmpty()) {
                continue;
            }
            builder.append(line.getLine()).append("\n");
        }
        return builder.toString();
    }

}
