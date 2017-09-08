package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedObjectCreator;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.schoellerfamily.gedbrowser.writer.GedWriter;

/**
 * @author Dick Schoeller
 */
public class GedWriterTest {
    /**
     * The file name to use in the test.
     */
    private static final String FILE_NAME =
            "/var/lib/gedbrowser/mini-schoeller.ged";
// Tests can be done with these others.
//            "/var/lib/gedbrowser/schoeller.ged";
//            "gl120368.ged";

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private Root root;

    /** */
    private String filename;

    /** */
    @Before
    public void setUp() throws IOException {
        final AbstractGedLine top = readFileTestSource();
        final GedObjectCreator g2g = new GedObjectCreator();
        root = g2g.create(top);
        logger.info("dbName: " + root.getDbName());
        filename = root.getDbName() + ".ged";
        root.setFilename("/tmp/" + filename);
        cleanTemp(filename);
    }

    /**
     * @throws IOException if file IO fails
     */
    @Test
    public void test() throws IOException {
        final GedWriter writer = new GedWriter(root);
        writer.write();
        final GedWriter writer1 = new GedWriter(root);
        writer1.write();
        final GedWriter writer2 = new GedWriter(root);
        writer2.write();
        assertSuccess();
    }

    /** */
    private void assertSuccess() throws IOException {
        logger.info("originalFilename: " + FILE_NAME);
        logger.info("filename: " + filename);
        final File original = new File(FILE_NAME);
        final File result = new File("/tmp/" + filename);
        assertTrue("File content should match",
                FileUtils.contentEquals(original, result));
        final File backup1 = new File("/tmp/" + filename + ".1");
        assertTrue("Backup should exist", backup1.exists());
        final File backup2 = new File("/tmp/" + filename + ".2");
        assertTrue("Backup should exist", backup2.exists());
    }

    /** */
    @After
    public void cleanUp() {
        cleanTemp(filename);
    }

    /**
     * Clean up the files that might be sitting in the temp directory.
     *
     * @param filename the filename that we'll be doing stuff with
     */
    private void cleanTemp(final String filename) {
        final File folder = new File("/tmp");
        final File[] files = folder.listFiles(new FilenameFilter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches(filename + ".*");
            }
        });
        for (final File delFile : files) {
            if (!delFile.delete()) {
                logger.error("Can't delete file " + delFile.getName());
            }
        }
    }

    /**
     * Read data for tests available to prepare data for tests.
     *
     * @return a populated GedLine parse tree.
     * @throws IOException because reader might throw.
     */
    private static AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(
                "", FILE_NAME);
    }
}
