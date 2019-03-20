package org.flabs.streamer;

import io.vertx.core.Vertx;
import org.flabs.web.WebVerticle;

public class Main {

    public static void main(String ... args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebVerticle());

    }
}
