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
public class SubmittersControllerTest implements MenuTestHelper {
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
    public final void testSubmittersControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submitters?db=gl120368";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Submitters - gl120368</title>")
            .contains("Submitters for dataset: gl120368</h2>")
            .contains("href=\"submitter?db=gl120368&amp;id=U1\">Phil Williams")
            .contains("href=\"submitter?db=gl120368&amp;id=U3\"> ")
            .contains("href=\"submitter?db=gl120368&amp;id=U14\">A K Robinson")
            .contains("href=\"submitter?db=gl120368&amp;id=U15\">Michael ")
            .contains("href=\"submitter?db=gl120368&amp;id=U2\">Arthur PUNCHA")
            .contains("href=\"submitter?db=gl120368&amp;id=U4\">Created by Fa")
            .contains("href=\"submitter?db=gl120368&amp;id=U5\">Paul Alger")
            .contains("href=\"submitter?db=gl120368&amp;id=U6\">Jim Beecroft")
            .contains("href=\"submitter?db=gl120368&amp;id=U7\">ken stel")
            .contains("href=\"submitter?db=gl120368&amp;id=U8\">Mark Willis B")
            .contains("href=\"submitter?db=gl120368&amp;id=U9\">Patricia Fisc")
            .contains("href=\"submitter?db=gl120368&amp;id=U10\">Hirt-Klooze")
            .contains("href=\"submitter?db=gl120368&amp;id=U11\">Lester LeMay")
            .contains("href=\"submitter?db=gl120368&amp;id=U12\">David A. Blo")
            .contains("href=\"submitter?db=gl120368&amp;id=U13\">Dave Morris")
            .contains(getMenu("A"));
    }

    /** */
    @Test
    public final void testSubmittersControllerBadDataSet() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/gedbrowser/submitters?db=XYZZY",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");
    }
}
