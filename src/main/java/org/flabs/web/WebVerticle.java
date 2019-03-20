package org.flabs.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class WebVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {


        vertx.createHttpServer()
                .requestHandler(r -> {
                    r.response().end("<h1>Hello World</h1>");
                })
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });

    }
}
