package org.flabs.service.cluster.impl;

import com.hazelcast.core.Member;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.flabs.service.cluster.ClusterAwareService;
import org.flabs.service.cluster.ClusterMember;
import org.flabs.service.cluster.ClusterMemberList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClusterAwareServiceImpl implements ClusterAwareService {

    private final HazelcastClusterManager clusterManager;

    public ClusterAwareServiceImpl(HazelcastClusterManager clusterManager) {
        this.clusterManager = clusterManager;
    }

    @Override
    public ClusterAwareService getClusterMembers(Handler<AsyncResult<ClusterMemberList>> handler) {
        Set<Member> members = clusterManager.getHazelcastInstance().getCluster().getMembers();
        if(members == null ) {
            handler.handle(Future.failedFuture("Unable to extract cluster members"));
        } else {
            final List<ClusterMember> collect = members.stream().map(m -> new ClusterMember(new JsonObject())).collect(Collectors.toList());

            handler.handle(Future.succeededFuture(new ClusterMemberList(collect)));
        }
        return this;
    }
}
