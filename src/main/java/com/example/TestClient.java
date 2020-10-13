package com.example;

import io.micronaut.http.HttpAttributes;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.uri.UriTemplate;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class TestClient {
    @Inject @Client("otherapp") private RxHttpClient client;

    public Single<String> get(String path) {
        try {
            var template = UriTemplate.of("/otherapp/{path}");
            var request = HttpRequest.GET(template.expand(Map.of("path", path)))
                    .setAttribute(HttpAttributes.URI_TEMPLATE, template);
            return client.retrieve(request).singleOrError();
        } catch (HttpClientResponseException e) {
            return Single.just("");
        }
    }

}
