package com.example.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-10
 */
@RestController
@RequestMapping(value = "/books")
public class BookResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Book> getAllBooks() {
        return Flux.just(new Book(1L,"book-a","author1"),new Book(2L,"book-b","author2"));
    }

    @GetMapping(value = "/{id}")
    public Mono<Book> findById(@PathVariable Long id) {
        return Mono.just(new Book(id,"book-a","author1"));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> save(@RequestBody Book book) {
        return findById(1L);
    }

    @DeleteMapping(value = "/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return Mono.empty();
    }

    @Value("${eureka.instance.metadata-map.version}")
    String version;

    @GetMapping("/version")
    public Mono<String> getVersion(){
        return Mono.just(version);
    }
}
