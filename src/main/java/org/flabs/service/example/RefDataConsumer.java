package org.flabs.service.example;

import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.servicediscovery.ServiceReference;
import org.flabs.refdata.currency.service.CurrencyReferenceDataService;
import org.flabs.service.AbstractServiceVerticle;

public class RefDataConsumer extends AbstractServiceVerticle {
    @Override
    public Completable rxStart() {
        //vertx.periodicStream(1000).toObservable().subscribe(timerId -> {
            discoSvc.rxGetRecord(new JsonObject().put("name", CurrencyReferenceDataService.SERVICE_NAME))
                    .subscribe(record -> {
                        ServiceReference reference = discoSvc.getReference(record);
                        CurrencyReferenceDataService as = reference.getAs(CurrencyReferenceDataService.class);
                        as.getCurrencyPairs().subscribe(arr-> {
                            System.out.println("Fetched some currency pairs");
                        }, err-> {
                            System.err.println("Error when fetching currency pairs");
                        });


                    });
       // });

        return super.rxStart();
    }
}
