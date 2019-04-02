package org.flabs.web;



import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import io.vertx.reactivex.ext.web.RoutingContext;
import org.flabs.repository.RatesRepository;

import java.util.List;

public class FxRatesRestHandler implements Handler<RoutingContext> {

    private final RatesRepository ratesRepository;

    public FxRatesRestHandler(RatesRepository ratesRepository) {
        this.ratesRepository = ratesRepository;
    }

    @Override
    public void handle(RoutingContext event) {
        final List<JsonObject > rates = ratesRepository.getRates();
        event.response().end(new JsonArray(rates).encode());

    }
}
