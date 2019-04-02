package org.flabs.streamer;

import io.reactivex.Observable;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import org.flabs.web.WebVerticle;

import java.util.concurrent.TimeUnit;

public class Main {

    private final int httpPort;

    Main(int httpPort) {
        this.httpPort = httpPort;
    }

    //TODO: understand more about rxjava2, example: disposable

    public static void main(String... args) {
        final Main main = new Main(args != null && args.length > 0 ? Integer.parseInt(args[0]) : 8080);
        main.start();
    }

    @SuppressWarnings("all")
    private void start() {
        Vertx.rxClusteredVertx(new VertxOptions()
                .setMaxEventLoopExecuteTime(6)
                .setMaxEventLoopExecuteTimeUnit(TimeUnit.SECONDS))
                .subscribe(vertx -> {
                    Observable.merge(vertx.rxDeployVerticle(new WebVerticle(),
                            new DeploymentOptions()

                                    .setConfig(new JsonObject()
                                            .put("http.port", this.httpPort)))
                                    .toObservable(),
                            vertx.rxDeployVerticle("org.flabs.streamer.StreamingVerticle",
                                    new DeploymentOptions().setHa(true))
                                    .toObservable())
                            .subscribe(verticleId -> {
                                        System.out.println("Component " + verticleId + " deployed");
                                    }, err -> {
                                        System.err.println("Error when starting component: " + err);
                                    },
                                    () -> {
                                        System.out.println("ALl components started");
                                    });
                }, System.err::println);
    }
}
