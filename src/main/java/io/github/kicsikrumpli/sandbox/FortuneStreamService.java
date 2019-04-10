package io.github.kicsikrumpli.sandbox;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class FortuneStreamService {
    private final WebClient fortuneClient;

    public FortuneStreamService(@Value("${fortune.api.url}") String url) {
        this.fortuneClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }


    public Flux<String> fetch(Integer numberOfCookies) {
        return Flux.interval(Duration.ofSeconds(2))
                .take(numberOfCookies)
                .flatMap(aLong -> fortuneClient
                        .get()
                        .retrieve()
                        .bodyToMono(Fortune.class))
                .map(Fortune::getFortune);
    }
}
