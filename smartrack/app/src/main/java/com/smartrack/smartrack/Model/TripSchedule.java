package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;

public class TripSchedule extends RealmObject {
    private int tripDay;
    private ObjectId placeId;
    private int placeOrder;

    public TripSchedule() {
    }

    public TripSchedule(int tripDay, ObjectId placeId, int placeOrder) {
        this.tripDay = tripDay;
        this.placeId = placeId;
        this.placeOrder = placeOrder;
    }

    public int getTripDay() {
        return tripDay;
    }

    public void setTripDay(int tripDay) {
        this.tripDay = tripDay;
    }

    public ObjectId getPlaceId() {
        return placeId;
    }

    public void setPlaceId(ObjectId placeId) {
        this.placeId = placeId;
    }

    public int getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(int placeOrder) {
        this.placeOrder = placeOrder;
    }
}
