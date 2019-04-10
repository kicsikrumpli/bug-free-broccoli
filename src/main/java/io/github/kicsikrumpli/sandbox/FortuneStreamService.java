package io.github.kicsikrumpli.sandbox;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Component
public class FortuneStreamService {
    private final WebClient fortuneClient;
    private long requestDelayMillis;

    public FortuneStreamService(
            @Value("${fortune.api.url}") String url,
            @Value("${fortune.api.delaymillis}") long requestDelayMillis) {

        this.requestDelayMillis = requestDelayMillis;
        this.fortuneClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public Flux<String> fetch(long bound) {
        return Flux.interval(Duration.ofMillis(requestDelayMillis))
                .take(bound)
                .flatMap(aLong -> fortuneClient
                        .get()
                        .retrieve()
                        .bodyToMono(Fortune.class))
                .map(Fortune::getFortune);
    }
}
