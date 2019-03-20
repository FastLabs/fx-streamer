package org.flabs.streamer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.flabs.web.WebVerticle;

public class Main {

    public static void main(String ... args) {
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebVerticle(), new DeploymentOptions(), r -> {
            if(r.succeeded()) {
                System.out.println("Web is up and running");
            } else {
                System.out.println("Error when starting web server");
            }
        });

        vertx.deployVerticle(new StreamingVerticle(), new DeploymentOptions(), r -> {
            if(r.succeeded()) {
                System.out.println("Message streamer deployed");
            } else {

            }
        });

    }
}
