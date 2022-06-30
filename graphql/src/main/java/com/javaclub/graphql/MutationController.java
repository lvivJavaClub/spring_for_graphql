package com.javaclub.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MutationController {
    private final Map<Integer, User> db = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    record User(Integer id, String name, Integer age) {}

    @QueryMapping
    List<User> users() {
        return new ArrayList<>(db.values());
    }

    @MutationMapping
    User addUser(@Argument User input) {
        int id = this.id.incrementAndGet();
        User user = new User(id, input.name, input.age);
        db.put(id, user);
        return user;
    }
}
