package io.github.kicsikrumpli.sandbox;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class FortuneControllerTest {
    @Autowired
    WebTestClient client;

    @Test
    public void test() {
        Flux<String> response = client
                .get()
                .uri("/stream/fortune?num=3")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();
        StepVerifier.create(response.log())
                .expectNextCount(3)
                .verifyComplete();
        /*
        StepVerifier.withVirtualTime(() -> response.log())
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(10))
                .expectNextCount(3)
                .verifyComplete();
        */
    }
}