package org.flabs.service;

import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.servicediscovery.ServiceDiscovery;

public class AbstractServiceVerticle extends AbstractVerticle {

    protected ServiceDiscovery discoSvc;

    public void setDiscoService(ServiceDiscovery discovery) {
        this.discoSvc = discovery;
    }
}
