package org.schoellerfamily.gedbrowser.security.test;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.schoellerfamily.gedbrowser.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @author Dick Schoeller
 */
@Configuration
public class MockMvcConfig {
    /** */
    @Autowired
    private WebApplicationContext wac;

    /** */
    @Autowired
    private TokenAuthenticationFilter filter;

//    /** */
//    @Autowired
//    private Environment env;

    /** */
    private final int port = 8080;

    /**
     * @return the request builder
     */
    public RequestBuilder mockRequestBuilder() {
        return null;
    }

    /**
     * @return the mock MVC
     */
    @Bean
    public MockMvc mockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
        return builder.addFilters(filter)
                .build();
    }

    /**
     * Stuff to do after construction.
     */
    @PostConstruct
    protected void restAssured() {
        RestAssuredMockMvc.mockMvc(mockMvc());
        RestAssured.port = this.port;
    }
}
