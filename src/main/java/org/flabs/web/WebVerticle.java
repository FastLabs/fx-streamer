package org.flabs.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import org.flabs.repository.RatesRepository;

public class WebVerticle extends AbstractVerticle {


    private Router getApiRouter() {
        final Router apiRouter = Router.router(vertx);
        apiRouter.route().handler(BodyHandler.create());
        apiRouter.route().consumes("application/json");
        apiRouter.route().produces("application/json");
        apiRouter.route("/fxrates").handler( new FxRatesRestHandler(new RatesRepository()));
        return apiRouter;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final Router router = Router.router(vertx);
        final SockJSHandlerOptions sjsOptions = new SockJSHandlerOptions().setHeartbeatInterval(2000);
        final SockJSHandler sockJSHandler = SockJSHandler.create(vertx, sjsOptions);
        final BridgeOptions options = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddressRegex("tick-address.*"))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("tick-address.*"));
        sockJSHandler.bridge(options);

        router.route("/*")
                .handler(StaticHandler.create()
                        .setWebRoot("webroot/fx-shell/resources/public/")
                        .setCachingEnabled(false)
                        .setIndexPage("index.html"))
        ;
        router.mountSubRouter("/api/v1", getApiRouter());
        router.route("/tick/*")
                .handler(sockJSHandler);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });

    }
}
