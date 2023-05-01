package com.graphtype.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class HelloController {
    @GetMapping("/")
    Flux<String> hello() {
        return Flux.just("Hello", "World");
    }


//    Server-Sent Event
    // curl -i localhost:8080/stream -H 'Accept: text/event-stream'
    // curl -i localhost:8080/stream -H 'Accept: application/stream+json'
    @GetMapping("/stream")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
//        return Flux.fromStream(stream.limit(10))
        return Flux.fromStream(stream)
                .map(i -> Collections.singletonMap("value", i));
    }

//    https://www.devkuma.com/docs/spring-webflux/
    @PostMapping("/echo")
    Mono<String> echo(@RequestBody Mono<String> body) {
        return body.map(String::toUpperCase);
    }
}
