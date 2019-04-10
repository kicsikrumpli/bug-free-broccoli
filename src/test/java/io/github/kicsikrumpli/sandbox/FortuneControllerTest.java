package io.github.kicsikrumpli.sandbox;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.WireMockServer;

import lombok.extern.slf4j.Slf4j;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@Slf4j
@ActiveProfiles("IT")
public class FortuneControllerTest {
    @Autowired
    WebTestClient client;
    @Autowired
    WireMockServer wiremockServer;

    @Before
    public void setUp() {
        var stubResponse = aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"fortune\" : \"dummy fortune\"}");

        wiremockServer.stubFor(get("/api/fortune").willReturn(stubResponse));
    }

    @Test
    public void test() {
        StepVerifier.create(client
                        .get()
                        .uri("/stream/fortune?num=3")
                        .accept(TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(String.class)
                        .getResponseBody()
                        .log())
                .expectNext("dummy fortune", "dummy fortune", "dummy fortune")
                .verifyComplete();
    }
}