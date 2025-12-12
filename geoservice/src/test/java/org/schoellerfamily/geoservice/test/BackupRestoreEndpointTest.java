package org.schoellerfamily.geoservice.test;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.schoellerfamily.geoservice.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BackupRestoreEndpointTest {
    /**
     * Management port.
     */
    @LocalManagementPort
    private int mgt;

    /**
     * Not sure what this is good for.
     */
    @Autowired
    private TestRestTemplate testRestTemplate;

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToBackupEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + mgt + "/actuator/backup",
                String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("backup succeeded to/from")
            .contains("locations in the cache");
    }

    /** */
    @Test
    public final void shouldReturn200WhenSendingRequestToRestoreEndpoint() {
        final ResponseEntity<String> entity = testRestTemplate.getForEntity(
                "http://localhost:" + this.mgt + "/actuator/restore", String.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("restore succeeded to/from")
            .contains("locations in the cache");
    }
}
