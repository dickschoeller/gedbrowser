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
public class HeadControllerTest implements MenuTestHelper {
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
    public final void testHeadController() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/head?db=gl120368";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Header - gl120368</title>")
            .contains("File:</span> C:\\Users\\Phil\\Documents\\W0803.GED")
            .contains("GEDCOM:</span> 5.5, LINEAGE-LINKED")
            .contains("Character Set:</span> ANSI")
            .contains("Destination:</span> FTM")
            .contains("Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=gl120368&amp;id=U1\">Phil Williams"
                    + " [U1]</a>")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testHeadControllerSchoeller() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/head?db=mini-schoeller";
        final ResponseEntity<String> entity =
                testRestTemplate.getForEntity(url, String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("<title>Header - mini-schoeller</title>")
            .contains("Submitter:</span> <a class=\"name\""
                    + " href=\"submitter?db=mini-schoeller&amp;"
                    + "id=SUB1\">Richard Schoeller [SUB1]</a>")
            .contains("GEDCOM:</span> 5.5.1, LINEAGE-LINKED")
            .contains("Destination:</span> GED55")
            .contains("Date:</span> 16 FEB 2001 22:04</li>")
            .contains("Character Set:</span> UTF-8")
            .contains(getMenu("mini-schoeller", "A"));
    }

    /** */
    @Test
    public final void testHeadControllerBadDataSet() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/gedbrowser/head?db=XYZZY",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }
}
