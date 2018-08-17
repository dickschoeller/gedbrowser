package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.NoteCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiNote;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.loader.GedDocumentFileLoader;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Dick Schoeller
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
public class NoteCrudTest {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient GedDocumentFileLoader loader;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    private NoteCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @Before
    public void setUp() {
        helper = new CrudTestHelper(
                new PersonCrud(loader, toDocConverter, repositoryManager),
                new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new NoteCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    public final void testReadNotesGl120368() {
        logger.info("Beginning testReadNotesGl120368");
        final List<ApiNote> list = crud.readAll(helper.getDb());
        then(list.get(0).getString()).isEqualTo("N1");
        then(list.get(0).getTail()).isEqualTo("_P_CCINFO 1-1319");
        then(list.get(1).getString()).isEqualTo("N2");
        then(list.get(1).getTail()).isEqualTo("_P_CCINFO 1-1319");
    }

    /** */
    @Test
    public final void testReadNotesGl120368N13() {
        logger.info("Beginning testReadNotesGl120368N13");
        final ApiNote resNote = crud.readNote(helper.getDb(), "N13");
        then(resNote.getString()).isEqualTo("N13");
        final String expected = "_P_CCINFO 1-1319\n"
                + "Suffolk County Record Office, Parish Register, St Mary"
                + " Magdalene, Debenham, Suffolk, England, Baptisms 1813-\n"
                + "1864 (FB47/D1/10), Samuel son of William Amass & Marianne"
                + " Thurston alias Amass of Debenham Butcher was\n"
                + "baptised by George Smalley vicar on 23/3/1828.";
        then(resNote.getTail()).isEqualTo(expected);
    }

    /** */
    @Test
    public final void testReadNotesGl120368N66() {
        logger.info("Beginning testReadNotesGl120368N66");
        final ApiNote resNote = crud.readNote(helper.getDb(), "N66");
        then(resNote.getString()).isEqualTo("N66");
        then(resNote.getTail()).isEqualTo("_P_CCINFO 1-1319");
        final ApiAttribute changed = resNote.getAttributes().get(0);
        then(changed.getType()).isEqualTo("attribute");
        then(changed.getString()).isEqualTo("Changed");
        then(changed.getAttributes().get(0).getType()).isEqualTo("date");
        then(changed.getAttributes().get(0).getString())
                .isEqualTo("22 APR 2007");
    }

    /** */
    @Test
    public final void testReadNotesGl120368N1932() {
        logger.info("Beginning testReadNotesGl120368N1932");
        final ApiNote resNote = crud.readNote(helper.getDb(), "N1932");
        then(resNote.getString()).isEqualTo("N1932");
        final String expected = "Prince Philip, born at Mon Repos, Corfu 10"
                + " June 1921, renounced his rights to the throne of Greece"
                + " and was naturalised in Great Britain taking the surname of"
                + " Mountbatten 28 Feb. 1947, created Duke of Edinburgh, Earl"
                + " of Merioneth and Baron Greenwich in the Peerage of the"
                + " United Kingdom 19 Nov. 1947, granted the style and dignity"
                + " of Prince of the United Kingdom of Great Britain and"
                + " Northern Ireland 22 Feb. 1957, Knight of the Garter"
                + " (1947), Knight of the Thistle (1952), Order of Merit"
                + " (1968), Privy Councillor (1951) and Privy Councillor of"
                + " Canada (1957) Admiral of the of the Fleet, Field Marshal"
                + " and Marshal of Royal Air Force, etc; m Westminster Abbey,"
                + " London 20 Nov. 1947, Elizabeth II, Queen of Great Britain"
                + " and Northern Ireland (born at 17 Bruton St, London W1 21"
                + " April 1926), and has issue (see GREAT BRITAIN - Almanach"
                + " de Gotha 1998; 1999; 2000).";
        then(resNote.getTail()).isEqualTo(expected);
        final List<ApiAttribute> attributes = resNote.getAttributes();
        then(attributes.get(0).getType()).isEqualTo("sourcelink");
        then(attributes.get(0).getString()).isEqualTo("S33734");
        then(attributes.get(0).getAttributes().get(0).getString())
                .isEqualTo("Note");
        then(attributes.get(0).getAttributes().get(0).getTail())
                .isEqualTo("Record originated in...");
        then(attributes.get(1).getType()).isEqualTo("sourcelink");
        then(attributes.get(1).getString()).isEqualTo("S33716");
        then(attributes.get(1).getAttributes().get(0).getString())
                .isEqualTo("Note");
        then(attributes.get(1).getAttributes().get(0).getTail())
                .isEqualTo("Record originated in...");
        then(attributes.get(2).getType()).isEqualTo("attribute");
        then(attributes.get(2).getString()).isEqualTo("Changed");
        then(attributes.get(2).getAttributes().get(0).getType())
                .isEqualTo("date");
        then(attributes.get(2).getAttributes().get(0).getString())
                .isEqualTo("8 MAR 2008");
    }

    /** */
    @Test
    public final void testReadNotesGl120368Xyzzy() {
        logger.info("Beginning testReadNotesGl120368N1932");
        try {
            final ApiNote resNote = crud.readNote(helper.getDb(), "Xyzzy");
            fail("The note should not be found: " + resNote.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object Xyzzy of type note not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testCreateNotesSimple() {
        logger.info("Beginning testCreateNotesSimple");
        final ApiNote reqNote = new ApiNote("note", "", "testing");
        final ApiNote resNote = crud.createNote(helper.getDb(), reqNote);
        then(resNote.getTail()).isEqualTo(reqNote.getTail());
    }

    /** */
    @Test
    public final void testDeleteNote() {
        logger.info("Beginning testDeleteNote");
        final ApiNote reqNote = new ApiNote("note", "", "this is a note");
        final ApiNote resNote = crud.createNote(helper.getDb(), reqNote);
        final String id = resNote.getString();
        final ApiNote deletedNote = crud.deleteNote(helper.getDb(), id);
        try {
            final ApiNote foundNote = crud.readNote(helper.getDb(),
                    deletedNote.getString());
            fail("should not have found note " + foundNote.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object " + id + " of type note not found",
                    e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteNoteNotFound() {
        logger.info("Beginning testDeleteNoteNotFound");
        try {
            final ApiNote foundNote = crud.readNote(helper.getDb(), "XXXXXXX");
            fail("should not have found note " + foundNote.getString());
        } catch (ObjectNotFoundException e) {
            assertEquals("Mismatched message",
                    "Object XXXXXXX of type note not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testDeleteNoteDatabaseNotFound() {
        logger.info("Beginning testDeleteNoteDatabaseNotFound");
        try {
            final ApiNote foundNote = crud.readNote("XYZZY", "N1");
            fail("should not have found note " + foundNote.getString());
        } catch (DataSetNotFoundException e) {
            assertEquals("Mismatched message",
                    "Data set XYZZY not found", e.getMessage());
        }
    }

    /** */
    @Test
    public final void testUpdateNoteWithNote() {
        logger.info("Beginning testUpdateNoteWithNote");
        final List<ApiAttribute> attributes = new ArrayList<>();
        attributes.add(new ApiAttribute("attribute", "Note", "first note"));
        final ApiNote reqNote = new ApiNote("note", "", attributes,
                "Top level note");
        final ApiNote resNote = crud.createNote(helper.getDb(), reqNote);
        then(resNote.getType()).isEqualTo(reqNote.getType());

        final ApiAttribute aNote = new ApiAttribute("attribute", "Note",
                "this is a note");
        resNote.getAttributes().add(aNote);
        final ApiNote updatedNote = crud.updateOne(helper.getDb(),
                resNote.getString(), resNote);
        assertEquals("attribute should be present", aNote,
                updatedNote.getAttributes().get(1));
    }
}
