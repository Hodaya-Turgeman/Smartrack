package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Trip extends RealmObject {
    @PrimaryKey
    private ObjectId _id;
    @Required
    private ObjectId travelerId;
    @Required
    private String tripName;
    private String tripDescription;
    @Required
    private String tripStartDate;
    @Required
    private String tripEndDate;
    @Required
    private String tripDays;
    @Required
    private String tripLocation;
    @Required
    private RealmList<PlaceDetails> tripPlacesList;
    @Required
    private RealmList<TripSchedule> tripSchedules;


    public Trip() {
    }

    public Trip(ObjectId _id, ObjectId travelerId, String tripName, String tripDescription, String tripStartDate, String tripEndDate, String tripDays, String tripLocation, RealmList<PlaceDetails> tripPlacesList, RealmList<TripSchedule> tripSchedules) {
        this._id = _id;
        this.travelerId = travelerId;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripDays = tripDays;
        this.tripLocation = tripLocation;
        this.tripPlacesList = tripPlacesList;
        this.tripSchedules = tripSchedules;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public ObjectId getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(ObjectId travelerId) {
        this.travelerId = travelerId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getTripDays() {
        return tripDays;
    }

    public void setTripDays(String tripDays) {
        this.tripDays = tripDays;
    }

    public String getTripLocation() {
        return tripLocation;
    }

    public void setTripLocation(String tripLocation) {
        this.tripLocation = tripLocation;
    }

    public RealmList<PlaceDetails> getTripPlacesList() {
        return tripPlacesList;
    }

    public void setTripPlacesList(RealmList<PlaceDetails> tripPlacesList) {
        this.tripPlacesList = tripPlacesList;
    }

    public RealmList<TripSchedule> getTripSchedules() {
        return tripSchedules;
    }

    public void setTripSchedules(RealmList<TripSchedule> tripSchedules) {
        this.tripSchedules = tripSchedules;
    }
}
