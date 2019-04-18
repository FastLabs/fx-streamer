package org.flabs.service;

import io.reactivex.Completable;
import io.vertx.core.json.JsonArray;
import io.vertx.reactivex.core.eventbus.MessageConsumer;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.types.MessageSource;
import org.flabs.refdata.currency.service.CurrencyReferenceDataService;

public class ReferenceDataService extends AbstractServiceVerticle {

    @Override
    public Completable rxStart() {
        final Record record = CurrencyReferenceDataService.createRecord();
        final MessageConsumer<Void> consumer = vertx.eventBus().consumer(CurrencyReferenceDataService.PROVIDER_ADDRESS);
        consumer.toObservable()
                .subscribe(msg -> {
                    System.out.println("Replying with some currency pairs");
                    msg.reply(new JsonArray());
                }, err -> {
                    System.err.println("Error listening reference data requests");
                });
        return discoSvc
                .rxPublish(record)
                .doOnSuccess(rec -> {
                    System.out.println("Ref data successfully deployed");
                })
                .ignoreElement();

    }
}
