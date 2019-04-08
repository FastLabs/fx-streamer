package org.flabs.service.cluster;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter =  true)
public class ClusterMember {

    private  String name;


    public ClusterMember(String name) {
        this.name = name;
    }

    public ClusterMember(JsonObject obj) {
        ClusterMemberConverter.fromJson(obj, this);
    }

    public String getName() {
        return name;
    }

    public ClusterMember setName(String name) {
        this.name = name;
        return this;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        ClusterMemberConverter.toJson(this, json);
        return json;
    }
}
