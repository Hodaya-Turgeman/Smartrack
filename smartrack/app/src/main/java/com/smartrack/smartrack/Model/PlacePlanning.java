package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlacePlanning extends PlaceDetails {
    private boolean status;



    private int day_in_trip;
    public PlacePlanning() {
    }

    public PlacePlanning(String placeID, String placeName, double placeLocationLat, double placeLocationLng, String placeFormattedAddress, String placeInternationalPhoneNumber, RealmList<String> placeOpeningHours, float placeRating, String placeWebsite, String placeImgUrl, boolean status) {
        super(placeID, placeName, placeLocationLat, placeLocationLng, placeFormattedAddress, placeInternationalPhoneNumber,  placeOpeningHours, placeRating,  placeWebsite,  placeImgUrl);
        this.status=status;
        this.day_in_trip = 0;
    }

    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getDay_in_trip() {
        return day_in_trip;
    }

    public void setDay_in_trip(int day_in_trip) {
        this.day_in_trip = day_in_trip;
    }
}
