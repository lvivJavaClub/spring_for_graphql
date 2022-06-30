package com.javaclub.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class QueryController {

    record Book(Integer id, String title, Integer pages) {}
    record Author(Integer id, String name){}

    List<Book> books = List.of(
            new Book(1, "Title A", 404),
            new Book(2, "Title B", 200),
            new Book(3, "Title C", 11)
    );

    @QueryMapping
    List<Book> books() {
        return books;
    }

    @QueryMapping
    Book bookById(@Argument Integer id) {
        return books.stream()
                .filter(b -> Objects.equals(b.id, id))
                .findFirst()
                .orElse(null);
    }

//    @SchemaMapping(typeName = "Book")
//    Author author(Book book){
//        System.out.println("get author for book id = " + book.id);
//        return new Author(book.id, Math.random() > .5 ? "Bender" : "Hermes");
//    }

//    @SchemaMapping(typeName = "Book")
//    Mono<Author> author(Book book){
//        System.out.println("get author for book id = " + book.id);
//        return Mono.just(new Author(book.id, Math.random() > .5 ? "Bender" : "Hermes"));
//    }

    @BatchMapping
    Map<Book, Author> author(List<Book> books) {
        System.out.println("get authors for books size = " + books.size());
        return books.stream()
                .collect(Collectors.toMap(b -> b, b -> new Author(b.id, Math.random() > .5 ? "Bender" : "Hermes")));
    }

}
