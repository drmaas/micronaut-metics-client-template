package com.example

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class ApplicationSpec extends Specification {

    @Inject
    EmbeddedApplication application

    @Inject
    @Client("/")
    HttpClient client

    void 'test it works'() {
        expect:
        application.running
    }

    void 'get metric names'() {
        when:
        String response = client.toBlocking().retrieve(HttpRequest.GET("/app"))

        then:
//        response == "/otherapp/5,/otherapp/4,/otherapp/3,/otherapp/2,/otherapp/1"
        response == "/otherapp/{path}"
    }

}
