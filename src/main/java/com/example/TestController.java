package com.example;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

import java.util.stream.Collectors;

@Controller("/app")
public class TestController {

    private final TestClient client;
    private final MeterRegistry meterRegistry;

    public TestController(TestClient client, MeterRegistry meterRegistry) {
        this.client = client;
        this.meterRegistry = meterRegistry;
    }

    @Get
    public Single<String> get() {
        return client.get("1")
                .flatMap(r -> client.get("2"))
                .flatMap(r -> client.get("3"))
                .flatMap(r -> client.get("4"))
                .flatMap(r -> client.get("5"))
                .map(r ->
                    meterRegistry.getMeters()
                            .stream()
                            .filter(meter -> meter.getId().getName().equalsIgnoreCase("http.client.requests"))
                            .flatMap(meter -> meter.getId().getTags().stream())
                            .filter(tag -> tag.getKey().equalsIgnoreCase("uri"))
                            .map(Tag::getValue)
                            .collect(Collectors.joining(","))
                );
    }
}
