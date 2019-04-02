package org.flabs.web;

import io.reactivex.Completable;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandlerOptions;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.handler.sockjs.SockJSHandler;
import org.flabs.repository.RatesRepository;

public class WebVerticle extends AbstractVerticle {

    private static final int DEFAULT_PORT = 8080;
    private HttpServer server;

    private void setServer(HttpServer server) {
        System.out.println("-------------");
        this.server = server;
    }

    private io.vertx.reactivex.ext.web.Router getApiRouter() {
        final Router apiRouter = Router.router(vertx);
        apiRouter.route().handler(BodyHandler.create());
        apiRouter.route().consumes("application/json");
        apiRouter.route().produces("application/json");
        apiRouter.route("/fxrates").handler(new FxRatesRestHandler(new RatesRepository()));
        return apiRouter;
    }


    @Override
    public Completable rxStart() {
        final int httpPort = config().containsKey("http.port") ? config().getInteger("http.port") : DEFAULT_PORT;
        final Router router = Router.router(vertx);
        final SockJSHandlerOptions sjsOptions = new SockJSHandlerOptions().setHeartbeatInterval(2000);
        final SockJSHandler sockJSHandler = SockJSHandler.create(vertx, sjsOptions);
        final BridgeOptions options = new BridgeOptions()
                .addInboundPermitted(new PermittedOptions().setAddressRegex("tick-address.*"))
                .addOutboundPermitted(new PermittedOptions().setAddressRegex("tick-address.*"));
        sockJSHandler.bridge(options);

        router.route("/*")
                .handler(StaticHandler.create()
                        .setWebRoot("public")
                        .setCachingEnabled(false)
                        .setIndexPage("index.html"))
        ;
        router.mountSubRouter("/api/v1", getApiRouter());
        router.route("/tick/*")
                .handler(sockJSHandler);

        return vertx.createHttpServer()
                .requestHandler(router)
                .rxListen(httpPort)
                .doAfterSuccess(this::setServer)
                .ignoreElement();
    }

    @Override
    public Completable rxStop() {
        return server.rxClose();
    }
}
