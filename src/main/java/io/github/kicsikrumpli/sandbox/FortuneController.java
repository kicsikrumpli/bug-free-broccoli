package io.github.kicsikrumpli.sandbox;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import javax.validation.constraints.Digits;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;

@Controller
public class FortuneController {
    private FortuneStreamService fortuneStream;

    public FortuneController(FortuneStreamService fortuneStream) {
        this.fortuneStream = fortuneStream;
    }

    @GetMapping(value = "/stream/fortune", produces = TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    @Validated
    public Flux<String> getFortuneStream(
            @Digits(integer = 2, fraction = 0)
            @RequestParam(name = "num", defaultValue = "10") Integer numberOfCookies) {

        return fortuneStream.fetch(numberOfCookies);
    }

}
