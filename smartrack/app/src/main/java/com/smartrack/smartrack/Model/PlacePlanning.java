package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlacePlanning {
    @PrimaryKey
    private ObjectId place_id;
    private String status;

    public PlacePlanning() {
    }

    public PlacePlanning(ObjectId _id, String status) {
        this.place_id = _id;
        this.status = status;
    }

    public ObjectId getPlace_id() {
        return place_id;
    }

    public void setPlace_id(ObjectId place_id) {
        this.place_id = place_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
