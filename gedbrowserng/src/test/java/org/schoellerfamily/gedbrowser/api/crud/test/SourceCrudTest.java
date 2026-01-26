package org.schoellerfamily.gedbrowser.api.crud.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.Application;
import org.schoellerfamily.gedbrowser.api.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.api.controller.exception.ObjectNotFoundException;
import org.schoellerfamily.gedbrowser.api.crud.FamilyCrud;
import org.schoellerfamily.gedbrowser.api.crud.PersonCrud;
import org.schoellerfamily.gedbrowser.api.crud.SourceCrud;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiAttribute;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiSource.ApiSourceBuilder;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.api.test.TestConfiguration;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Dick Schoeller
 */
@SpringBootTest(classes = { Application.class,
    TestConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "management.port=0" })
@SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
@Slf4j
public class SourceCrudTest {

    /** */
    @Autowired
    private transient GedObjectFileLoader loader;

    /** */
    @Autowired
    private transient GedObjectToGedDocumentMongoConverter toDocConverter;

    /** */
    @Autowired
    private transient RepositoryManagerMongo repositoryManager;

    /** */
    private SourceCrud crud;

    /** */
    private CrudTestHelper helper;

    /** */
    @BeforeEach
    public void setUp() {
        helper = new CrudTestHelper(new PersonCrud(loader, toDocConverter, repositoryManager),
            new FamilyCrud(loader, toDocConverter, repositoryManager));
        crud = new SourceCrud(loader, toDocConverter, repositoryManager);
    }

    /** */
    @Test
    void testReadSourcesGl120368() {
        log.info("Beginning testReadSourcesGl120368");
        final List<ApiSource> list = crud.readAll(helper.getDb());
        final ApiSource firstSource = list.get(0);
        assertThat(firstSource)
            .returns("S1688", o -> o.getString())
            .returns(true, o -> o.getImages().isEmpty())
            .returns("1841 England Census", o -> o.getTitle());
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        assertThat(attributes.get(0))
            .returns("attribute", o -> o.getType())
            .returns("Author", o -> o.getString())
            .returns("Ancestry.com", o -> o.getTail());
        assertThat(attributes.get(1))
            .returns("attribute", o -> o.getType())
            .returns("Title", o -> o.getString())
            .returns("1841 England Census", o -> o.getTail());
        assertThat(attributes.get(2))
            .returns("attribute", o -> o.getType())
            .returns("Published", o -> o.getString())
            .returns("Provo, UT, USA: The Generations Network, Inc., 2006", o -> o.getTail());
    }

    /** */
    @Test
    void testReadSourcesMiniSchoeller() {
        log.info("Beginning testReadSourcesMiniSchoeller");
        final List<ApiSource> list = crud.readAll("mini-schoeller");
        final ApiSource firstSource = list.get(0);
        assertThat(firstSource)
            .returns("S2", o -> o.getString())
            .returns(true, o -> o.getImages().isEmpty())
            .returns("Schoeller, Melissa Robinson, birth certificate", o -> o.getTitle());
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        assertThat(attributes.get(0))
            .returns("attribute", o -> o.getType())
            .returns("Title", o -> o.getString())
            .returns("Schoeller, Melissa Robinson, birth certificate", o -> o.getTail());
        assertThat(attributes.get(1))
            .returns("attribute", o -> o.getType())
            .returns("Abbreviation", o -> o.getString())
            .returns("SchoellerMelissaBirthCert", o -> o.getTail());
        assertThat(attributes.get(2))
            .returns("attribute", o -> o.getType())
            .returns("Note", o -> o.getString())
            .returns("We have the original of this document", o -> o.getTail());
    }

    /** */
    @Test
    void testReadSourcesMiniSchoellerS2() {
        log.info("Beginning testReadSourcesMiniSchoellerS2");
        final ApiSource firstSource = crud.readOne("mini-schoeller", "S2");
        assertThat(firstSource)
            .returns("S2", o -> o.getString())
            .returns(true, o -> o.getImages().isEmpty())
            .returns("Schoeller, Melissa Robinson, birth certificate", o -> o.getTitle());
        final List<ApiAttribute> attributes = firstSource.getAttributes();
        assertThat(attributes.get(0))
            .returns("attribute", o -> o.getType())
            .returns("Title", o -> o.getString())
            .returns("Schoeller, Melissa Robinson, birth certificate", o -> o.getTail());
        assertThat(attributes.get(1))
            .returns("attribute", o -> o.getType())
            .returns("Abbreviation", o -> o.getString())
            .returns("SchoellerMelissaBirthCert", o -> o.getTail());
        assertThat(attributes.get(2))
            .returns("attribute", o -> o.getType())
            .returns("Note", o -> o.getString())
            .returns("We have the original of this document", o -> o.getTail());
    }

    /** */
    @Test
    void testReadSourcesMiniSchoellerXyzzy() {
        log.info("Beginning testReadSourcesMiniSchoellerXyzzy");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne("mini-schoeller", "Xyzzy"))
            .withMessage("Object Xyzzy of type source not found");
    }

    /** */
    @Test
    void testCreateSourcesSimple() {
        log.info("Beginning testCreateSourcesSimple");
        final ApiSource inSource = ApiSource.builder()
            .type("source")
            .string("")
            .title("Unknown")
            .attributes(List.of())
            .build();
        final ApiSource newSource = crud.createOne(helper.getDb(), inSource);
        assertThat(newSource).returns(inSource.getType(), o -> o.getType());
    }

    /** */
    @Test
    void testDeleteSource() {
        log.info("Beginning testDeleteSource");
        final ApiSource reqSource = ApiSource.builder()
            .type("source")
            .string("")
            .title("Unknown")
            .attributes(List.of())
            .build();
        final ApiSource resSource = crud.createOne(helper.getDb(), reqSource);
        final String id = resSource.getString();
        final ApiSource deletedSource = crud.deleteOne(helper.getDb(), id);

        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.readOne("mini-schoeller", deletedSource.getString()))
            .withMessage("Object " + deletedSource.getString() + " of type source not found");
    }

    /** */
    @Test
    void testDeleteSourceNotFound() {
        log.info("Beginning testDeleteSourceNotFound");
        assertThatExceptionOfType(ObjectNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne(helper.getDb(), "XXXXXXX"))
            .withMessage("Object XXXXXXX of type source not found");
    }

    /** */
    @Test
    void testDeleteSubmitterDatabaseNotFound() {
        log.info("Beginning testDeleteSubmitterDatabaseNotFound");
        assertThatExceptionOfType(DataSetNotFoundException.class)
            .isThrownBy(() -> crud.deleteOne("XYZZY", "S1"))
            .withMessage("Data set XYZZY not found");
    }

    /** */
    @Test
    void testUpdateSourceWithNote() {
        log.info("Beginning testUpdateSourceWithNote");
        final List<ApiAttribute> attributes = List.of(ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("first note")
            .attributes(List.of())
            .build());
        final ApiSource inSource = ApiSource.builder()
            .type("source")
            .string("")
            .attributes(attributes)
            .title("Unknown")
            .build();
        final ApiSourceBuilder<?, ?> newSource = crud.createOne(helper.getDb(), inSource)
            .toBuilder();

        final ApiAttribute aNote = ApiAttribute.builder()
            .type("attribute")
            .string("Note")
            .tail("this is a note")
            .build();
        newSource.attribute(aNote);
        final ApiSource updatedSource = crud.updateOne(helper.getDb(), newSource.getString(),
            newSource.build());
        assertThat(updatedSource.getAttributes().get(1)).isEqualTo(aNote);
    }
}
