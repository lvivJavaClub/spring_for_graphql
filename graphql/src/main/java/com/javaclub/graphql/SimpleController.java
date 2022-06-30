package com.javaclub.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SimpleController {

    @SchemaMapping(typeName = "Query", field = "hello")
    String getMessage() {
        return "Hello, JavaClub!";
    }

    @QueryMapping
    String helloWithName(@Argument String name) {
        return "Hello, " + name;
    }

}
