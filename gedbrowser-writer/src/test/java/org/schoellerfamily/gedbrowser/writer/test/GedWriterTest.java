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
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.schoellerfamily.gedbrowser.writer.GedWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class GedWriterTest {
    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    /** */
    @Value("${gedbrowser.home:/var/lib/gedbrowser}")
    private transient String gedbrowserHome;

    /**
     * The file name to use in the test.
     */
    private String inputFilename;

    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    private Root root;

    /** */
    private String filename;

    /**
     * @throws IOException if there are problems reading the data file
     */
    @Before
    public void setUp() throws IOException {
        inputFilename = gedbrowserHome + "/mini-schoeller.ged";
        final AbstractGedLine top = readFileTestSource();
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

    /**
     * @throws IOException if there are problems reading or writing files
     */
    private void assertSuccess() throws IOException {
        logger.info("originalFilename: " + inputFilename);
        logger.info("filename: " + filename);
        final File original = new File(inputFilename);
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
     * @param baseFilename the filename that we'll be doing stuff with
     */
    private void cleanTemp(final String baseFilename) {
        final File folder = new File("/tmp");
        final File[] files = folder.listFiles(new FilenameFilter() {
            /**
             * {@inheritDoc}
             */
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches(baseFilename + ".*");
            }
        });
        if (files == null) {
            logger.info("No files found matching " + baseFilename + " in /tmp");
            return;
        }
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
    private AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(
                "", inputFilename);
    }
}
