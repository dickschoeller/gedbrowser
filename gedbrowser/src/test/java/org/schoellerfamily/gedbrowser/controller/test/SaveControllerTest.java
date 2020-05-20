package org.schoellerfamily.gedbrowser.controller.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.schoellerfamily.gedbrowser.Application;
import org.schoellerfamily.gedbrowser.datamodel.users.UserImpl;
import org.schoellerfamily.gedbrowser.datamodel.users.Users;
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
     * The data of known users.
     */
    @Autowired
    private Users<UserImpl> users;

    /**
     * Server port.
     */
    @LocalServerPort
    private int port;

    /** */
    @Test
    public final void testSaveControllerOK() {
        // This makes it so anonymous access has admin. That allows testing
        // stuff that only works for admin.
        final UserImpl user = new UserImpl();
        user.setUsername("anonymousUser");
        user.setPassword("guest");
        user.addRole("ADMIN");
        users.add(user);

        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=gl120368";
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

        // Turn off anonymous admin.
        users.remove(user);
    }

    /** */
    @Test
    public final void testSaveControllerDatasetNotFound() {
        // This makes it so anonymous access has admin. That allows testing
        // stuff that only works for admin.
        final UserImpl user = new UserImpl();
        user.setUsername("anonymousUser");
        user.setPassword("guest");
        user.addRole("ADMIN");
        users.add(user);

        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=XYZZY";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        then(entity.getBody()).contains("Data set not found");

        // Turn off anonymous admin.
        users.remove(user);
    }

    /** */
    @Test
    public final void testSaveControllerNotAdmin() {
        final String url = "http://localhost:" + port
                + "/gedbrowser/save?db=gl120368";
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(url,
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody())
            .contains("Sorry, you aren't authorized to do that!");
    }
}
