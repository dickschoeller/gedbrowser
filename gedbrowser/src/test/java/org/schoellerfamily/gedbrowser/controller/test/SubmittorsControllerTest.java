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
public class SubmittorsControllerTest {
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
    public final void testSubmttorsControllerOK() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/submittors?db=gl120368";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("<title>Submittors - gl120368</title>")
            .contains("Submittors for dataset: gl120368</h2>")
            .contains("href=\"submittor?db=gl120368&amp;id=U1\">Phil Williams")
            .contains("href=\"submittor?db=gl120368&amp;id=U3\"> ")
            .contains("href=\"submittor?db=gl120368&amp;id=U14\">A K Robinson")
            .contains("href=\"submittor?db=gl120368&amp;id=U15\">Michael ")
            .contains("href=\"submittor?db=gl120368&amp;id=U2\">Arthur  ")
            .contains("href=\"submittor?db=gl120368&amp;id=U4\">Created by")
            .contains("href=\"submittor?db=gl120368&amp;id=U5\">Paul Alger")
            .contains("href=\"submittor?db=gl120368&amp;id=U6\">Jim Beecroft")
            .contains("href=\"submittor?db=gl120368&amp;id=U7\">ken stel")
            .contains("href=\"submittor?db=gl120368&amp;id=U8\">Mark Willis B")
            .contains("href=\"submittor?db=gl120368&amp;id=U9\">Patricia Fisc")
            .contains("href=\"submittor?db=gl120368&amp;id=U10\">Hirt-Klooze")
            .contains("href=\"submittor?db=gl120368&amp;id=U11\">Lester LeMay")
            .contains("href=\"submittor?db=gl120368&amp;id=U12\">David A. Blo")
            .contains("href=\"submittor?db=gl120368&amp;id=U13\">Dave Morris");
        }

}
