package org.flabs.streamer;

import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import org.flabs.web.WebVerticle;

public class Main {

    private final int httpPort;

    Main(int httpPort) {
        this.httpPort = httpPort;
    }

    public static void main(String... args) {
        final Main main = new Main(args != null && args.length > 0 ? Integer.parseInt(args[0]): 8080);
        Vertx.clusteredVertx(new VertxOptions(), main::onStart);

    }

    void onStart(AsyncResult<Vertx> res) {
        if (res.succeeded()) {
            final Vertx vertx = res.result();
            vertx.deployVerticle(new WebVerticle(), new DeploymentOptions(), r -> {
                if (r.succeeded()) {
                    System.out.println("Web is up and running");
                } else {
                    System.out.println("Error when starting web server");
                }
            });

            vertx.deployVerticle(new StreamingVerticle(), new DeploymentOptions()
                    .setConfig(new JsonObject().put("http.port", 8080))
                    .setHa(true), r -> {
                if (r.succeeded()) {
                    System.out.println("Message streamer deployed");
                } else {

                }
            });
        } else {
            System.err.print("Unable to start in clustered mode");
        }
    }
}
