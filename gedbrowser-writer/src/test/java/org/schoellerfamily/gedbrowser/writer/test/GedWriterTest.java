package org.schoellerfamily.gedbrowser.writer.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.AbstractGedLine;
import org.schoellerfamily.gedbrowser.reader.GedLineToGedObjectTransformer;
import org.schoellerfamily.gedbrowser.reader.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.reader.testreader.TestResourceReader;
import org.schoellerfamily.gedbrowser.writer.GedWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;



/**
 * Contains tests for ged writer.
 *
 * @author Richard Schoeller
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestConfiguration.class })
@Slf4j
class GedWriterTest {
    /** */
    @Autowired
    private transient GedLineToGedObjectTransformer g2g;

    /** */
    @Value("${gedbrowser.home:#{ systemProperties['user.dir'] }/src/test/resources}")
    private transient String gedbrowserHome;

    /**
     * The file name to use in the test.
     */
    private String inputFilename;

    /** */
    private Root root;

    /** */
    private String filename;

    @BeforeEach
    void setUp() throws IOException {
        inputFilename = gedbrowserHome + "/mini-schoeller.ged";
        final AbstractGedLine top = readFileTestSource();
        root = g2g.create(top);
        log.info("dbName: {}", root.getDbName());
        filename = root.getDbName() + ".ged";
        root.setFilename("/tmp/" + filename);
        cleanTemp(filename);
    }

    @Test
    void test() throws IOException {
        final GedWriter writer = new GedWriter(root);
        writer.write();
        final GedWriter writer1 = new GedWriter(root);
        writer1.write();
        final GedWriter writer2 = new GedWriter(root);
        writer2.write();
        assertSuccess();
    }

    @Test
    void testString() throws IOException {
        final GedWriter writer = new GedWriter(root);
        final String writeString = writer.writeString();
        final String readString = FileUtils.readFileToString(new File(inputFilename),
            StandardCharsets.UTF_8);
        assertEquals(readString, writeString, "Input and output should match");
    }

    private void assertSuccess() throws IOException {
        log.info("originalFilename: {}", inputFilename);
        log.info("filename: {}", filename);
        final File original = new File(inputFilename);
        final File result = new File("/tmp/" + filename);
        assertTrue(FileUtils.contentEquals(original, result), "File content should match");
        final File backup1 = new File("/tmp/" + filename + ".1");
        assertTrue(backup1.exists(), "Backup should exist");
        final File backup2 = new File("/tmp/" + filename + ".2");
        assertTrue(backup2.exists(), "Backup should exist");
    }

    @AfterEach
    void tearDown() {
        cleanTemp(filename);
    }

    private void cleanTemp(final String baseFilename) {
        final File folder = new File("/tmp");
        final File[] files = folder.listFiles(new FilenameFilter() {
            /**
             * Returns the boolean.
             *
             * @param dir the dir
             * @param name the name to use
             * @return the resulting boolean
             */
            @Override
            public boolean accept(final File dir, final String name) {
                return name.matches(baseFilename + ".*");
            }
        });
        if (files == null) {
            log.info("No files found matching {} in /tmp", baseFilename);
            return;
        }
        for (final File delFile : files) {
            if (!delFile.delete()) {
                log.error("Can't delete file {}", delFile.getName());
            }
        }
    }

    private AbstractGedLine readFileTestSource() throws IOException {
        return TestResourceReader.readFileTestSource(inputFilename);
    }
}
