package org.schoellerfamily.gedbrowser.writer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.CharsetScanner;
import org.schoellerfamily.gedbrowser.writer.creator.GedObjectToGedWriterVisitor;

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
            backup();
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
     * Save the existing version of the file by renaming it to something ending
     * with .&lt;number&gt;.
     *
     * @throws IOException if the rename fails
     */
    private void backup() throws IOException {
        final File dest = createFile(root.getFilename());
        if (dest.exists()) {
            final File backupFile = generateBackupFilename();
            if (!dest.renameTo(backupFile)) {
                throw new IOException("Could not rename file from "
                        + dest.getName() + " to " + backupFile.getName());
            }
        }
    }

    /**
     * @return the filename.n that doesn't exist
     */
    private File generateBackupFilename() {
        int i = 1;
        while (true) {
            final File backupFile = createFile(root.getFilename() + "." + i);
            if (!backupFile.exists()) {
                return backupFile;
            }
            i++;
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
     * @param filename the name of the file to create
     * @return the file object
     */
    private File createFile(final String filename) {
        return new File(filename);
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
