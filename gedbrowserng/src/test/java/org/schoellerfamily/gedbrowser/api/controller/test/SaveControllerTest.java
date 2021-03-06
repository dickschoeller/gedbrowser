package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.api.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class SaveControllerTest {
    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    public final void testSaveControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/gl120368/save";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("0 HEAD")
            .contains("1 SOUR FAMILY_HISTORIAN")
            .contains("2 VERS 3.1")
            .contains("2 NAME Family Historian")
            .contains("2 CORP Calico Pie Limited")
            .contains("1 FILE C:\\Users\\Phil\\Documents\\W0803.GED")
            .contains("1 GEDC")
            .contains("2 VERS 5.5")
            .contains("2 FORM LINEAGE-LINKED")
            .contains("1 CHAR ANSI")
            .contains("1 DEST FTM");
    }

    /** */
    @Test
    public final void testSaveControllerDatasetNotFound() {
        final String url = "http://localhost:" + port
                + "/gedbrowserng/v1/dbs/xyzzy/save";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set xyzzy not found");
    }
}
