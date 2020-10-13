package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Single;

@Controller("/otherapp")
public class OtherController {

    @Get("/{path}")
    public Single<String> get(@PathVariable String path) {
        return Single.just(path);
    }

}
