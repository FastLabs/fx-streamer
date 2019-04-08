package org.flabs.service.cluster;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.flabs.service.cluster.impl.ClusterAwareServiceImpl;

@ProxyGen
@VertxGen
public interface ClusterAwareService {

    @GenIgnore
    static ClusterAwareService create(HazelcastClusterManager clusterManager) {
        return new ClusterAwareServiceImpl(clusterManager);
    }

    static ClusterAwareService createProxy(Vertx vertx, String address) {
        return new ClusterAwareServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    ClusterAwareService getClusterMembers(Handler<AsyncResult<ClusterMemberList>> handler);


}
