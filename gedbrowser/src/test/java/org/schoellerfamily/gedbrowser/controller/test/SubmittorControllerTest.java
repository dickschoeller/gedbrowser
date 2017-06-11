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
public class SubmittorControllerTest {
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
    public final void testSubmittorControllerU1() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submittor?db=gl120368&id=U1";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Submittor: U1"
                + " - Phil Williams");
    }

    /** */
    @Test
    public final void testSubmittorControllerU2() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submittor?db=gl120368&id=U2";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Submittor: U2"
                + " - Arthur /PUNCHARD/")
            .contains("Name:</span> Arthur  PUNCHARD")
            .contains("Changed:</span> 24 MAR 2007");
    }

    /** */
    @Test
    public final void testSubmittorControllerU4() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submittor?db=gl120368&id=U4";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Submittor: U4"
                + " - Created by FamilySearch")
            .contains("Name:</span> Created by FamilySearch")
            .contains("Address:</span> 50 East North Temple Street<br/>")
            .contains("Salt Lake City, Utah 84150");
    }
}
