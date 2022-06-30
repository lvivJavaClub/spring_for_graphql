package com.javaclub.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
class IntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void setupMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    // TODO: 30/06/2022 before run test comment RSocket section in application.properties
    @Test
    void test() {
        WebTestClient client =
                MockMvcWebTestClient.bindToApplicationContext(wac)
                        .configureClient()
                        .baseUrl("/graphql")
                        .build();

        HttpGraphQlTester tester = HttpGraphQlTester.create(client);

        GraphQlTester.EntityList<String> stringEntityList = tester.document("{books {title}}")
                .execute()
                .path("books.[*].title")
                .entityList(String.class)
                .hasSizeGreaterThan(1);
    }
}
