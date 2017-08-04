package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Application;
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
public class SubmitterControllerTest implements MenuTestHelper {
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
    public final void testSubmitterControllerU1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submitter?db=gl120368&id=U1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Phil Williams - U1 - gl120368</title>")
            .contains("Name:</span> Phil Williams")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testSubmitterControllerU2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submitter?db=gl120368&id=U2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Arthur PUNCHARD - U2 - gl120368</title>")
            .contains("Name:</span> Arthur PUNCHARD")
            .contains("Changed:</span> 24 MAR 2007")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testSubmitterControllerU4() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submitter?db=gl120368&id=U4";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Created by FamilySearch (TM) Internet"
                    + " Genealogy Service - U4 - gl120368</title>")
            .contains("Name:</span> Created by FamilySearch")
            .contains("Address:</span> 50 East North Temple Street<br/>")
            .contains("Salt Lake City, Utah 84150")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testSubmitterControllerBadDataSet() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                        + "/gedbrowser/submitter?db=XYZZY&id=U4",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }

    /** */
    @Test
    public final void testSubmitterControllerBadSubmitter() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port
                        + "/gedbrowser/submitter?db=gl120368&id=U99999",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Submitter not found")
        .contains(getMenu("A"));
    }
}
