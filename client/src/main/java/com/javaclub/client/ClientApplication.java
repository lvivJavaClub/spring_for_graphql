package com.javaclub.client;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.RSocketGraphQlClient;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    HttpGraphQlClient httpGraphQlClient() {
        return HttpGraphQlClient.builder().url("http://127.0.0.1:8080/graphql").build();
    }

    @Bean
    RSocketGraphQlClient rSocketGraphQlClient() {
        return RSocketGraphQlClient.builder().tcp("127.0.0.1", 9191).route("graphql").build();
    }

    @Bean
    ApplicationRunner applicationRunner(HttpGraphQlClient httpClient, RSocketGraphQlClient rSocketClient) {
        return args -> {
            var httpRequestDocument = """
                      query {
                    	  bookById(id : 2) {
                    		  id, title, pages
                    	  }
                      }
                    """;
            httpClient.document(httpRequestDocument)
                    .retrieve("bookById")
                    .toEntity(Book.class)
                    .subscribe(System.out::println);

            var rSocketRequestDocument = """
                    subscription {
                        greetings { greeting }
                    }
                    """;
            rSocketClient.document(rSocketRequestDocument)
                    .retrieveSubscription("greetings")
                    .toEntity(Greeting.class)
                    .subscribe(System.out::println);
        };
    }

    record Book(Integer id, String title, Integer pages) {}
    record Greeting(String greeting) {}
}
