package org.schoellerfamily.gedbrowser.reader.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.datamodel.GedObject;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.schoellerfamily.gedbrowser.datamodel.Place;
import org.schoellerfamily.gedbrowser.datamodel.Root;
import org.schoellerfamily.gedbrowser.reader.testreader.TestDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfiguration.class })
public class CharsetTest {
    /** */
    @Autowired
    private transient TestDataReader reader;

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testANSEL() throws IOException {
        final Root root = reader.readFileTestSource("ansel.ged");
        final Person person = root.find("CHILD0", Person.class);
        final GedObject attr = person.getAttributes().get(2);
        final Place subAttr = (Place) attr.getAttributes().get(0);
        final String place = subAttr.getString();
        assertEquals(
                "String not converted correctly",
                "slash l - uppercase (Ł), slash o - uppercase (Ø), slash d -"
                + " uppercase (Đ), thorn - uppercase (Þ)",
                place);
    }

    /**
     * @throws IOException if there is an error reading the file.
     */
    @Test
    public void testANSI() throws IOException {
        final Root root = reader.readFileTestSource("ansi.ged");
        final Person person = root.find("I380", Person.class);
        final GedObject attr = person.getAttributes().get(3);
        final Place subAttr = (Place) attr.getAttributes().get(1);
        final String place = subAttr.getString();
        assertEquals(
                "String not converted correctly",
                "South Australia on the Ábberton  1846 with daug & son in law",
                place);
    }
}
