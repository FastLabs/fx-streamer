package org.flabs.streamer.clustered;


import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class DataPipe {

    public static void main(String... args) {
        var options = new VertxOptions().setHAEnabled(true);
        Vertx.clusteredVertx(options, vAr -> {
            if(vAr.succeeded()) {

            } else {

            }
        });
    }
}
