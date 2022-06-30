package com.javaclub.graphql;

import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@Controller
public class SubscriptionController {
    record Greeting(String greeting){}

    @SubscriptionMapping
    Flux<Greeting> greetings() {
        return Flux.fromStream(Stream.generate(() -> new Greeting("Hello JavaClub @" + Instant.now().getNano())))
                .delayElements(Duration.ofSeconds(1))
                .take(15);
    }
}
