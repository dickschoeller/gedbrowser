package org.schoellerfamily.gedbrowser.writer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.writer.creator.GedObjectToGedWriterVisitor;
import org.schoellerfamily.gedbrowser.writer.util.Backup;

/**
 * @author Richard Schoeller
 */
public class GedWriter {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private final GedObjectToGedWriterVisitor visitor =
            new GedObjectToGedWriterVisitor();

    /** */
    private final Root root;

    /**
     * @param root the root of the data set to write.
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
            logger.error("Problem backing up old copy of GEDCOM file", e);
        }
        final String filename = root.getFilename();
        final String charset = new CharsetScanner().charset(root);
        try (FileOutputStream fstream = new FileOutputStream(filename);
                BufferedOutputStream bstream = new BufferedOutputStream(
                        fstream)) {
            writeTheLines(bstream, charset);
        } catch (IOException e) {
            logger.error("Problem writing GEDCOM file", e);
        }
    }

    /**
     * Loop through the lines from the line creator and write them to the
     * stream.
     *
     * @param stream the stream to write to
     * @param charset the string encoding to use
     * @throws IOException if there is a problem writing to the stream
     */
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
     * @return the gedcom file as a string
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
