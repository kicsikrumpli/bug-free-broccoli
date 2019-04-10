package io.github.kicsikrumpli.sandbox;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FortuneControllerTest {
    @MockBean
    private FortuneStreamService mockFortuneService;

    @Autowired
    private FortuneController underTest;

    @Before
    public void setUp() {
        given(mockFortuneService.fetch(anyLong())).willReturn(Flux.just("one", "two", "three"));
    }

    @Test
    public void test() {
        // GIVEN in setUp

        // WHEN
        Flux<String> fortuneStream = underTest.getFortuneStream(3);

        // THEN
        StepVerifier.create(fortuneStream)
                .expectNext("one", "two", "three")
                .verifyComplete();
    }
}