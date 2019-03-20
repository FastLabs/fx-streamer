package org.flabs.repository;

import io.vertx.core.json.JsonObject;

import java.util.Arrays;
import java.util.List;

public class RatesRepository {

    public List<JsonObject> getRates() {
        return Arrays.asList(new JsonObject().put("fromCurrency", "usd"));
    }
}
