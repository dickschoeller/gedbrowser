package org.schoellerfamily.gedbrowser.security.test;

import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;



/**
 * Configures components related to mock mvc.
 *
 * @author Richard Schoeller
 */
@Configuration
@RequiredArgsConstructor
public class MockMvcConfig {
    /** */
    private final WebApplicationContext wac;

    /** */
    private final TokenAuthenticationFilter filter;

    /** */
    private static final int PORT = 8080;

    /**
     * Returns the request builder.
     *
     * @return the resulting request builder
     */
    public RequestBuilder mockRequestBuilder() {
        return null;
    }

    private MockMvc mockMvc() {
        final DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        return builder.addFilter(filter, "", "")
            .build();
    }

    /**
     * Stuff to do after construction.
     */
    @PostConstruct
    @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    protected void restAssured() {
        RestAssuredMockMvc.mockMvc(mockMvc());
        RestAssured.port = PORT;
    }
}
