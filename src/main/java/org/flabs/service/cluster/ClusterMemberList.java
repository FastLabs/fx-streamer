package org.flabs.service.cluster;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

@DataObject(generateConverter = true)
public class ClusterMemberList {

    private List<ClusterMember> members;

    public ClusterMemberList(List<ClusterMember> members) {
        this.members = members;
    }

    public ClusterMemberList(JsonObject data) {
        ClusterMemberListConverter.fromJson(data, this);
    }

    public ClusterMemberList addMember(ClusterMember member) {

        if(members == null) {
            members = new ArrayList<>();
        }
        members.add(member);
        return this;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ClusterMemberListConverter.toJson(this, json);
        return json;
    }

}
