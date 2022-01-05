package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlacePlanning extends PlaceDetails {
    private boolean status;

    public PlacePlanning() {
    }

    public PlacePlanning(String placeID, String placeName, double placeLocationLat, double placeLocationLng, String placeFormattedAddress, String placeInternationalPhoneNumber, RealmList<String> placeOpeningHours, float placeRating, String placeWebsite, String placeImgUrl, boolean status) {
        super(placeID, placeName, placeLocationLat, placeLocationLng, placeFormattedAddress, placeInternationalPhoneNumber,  placeOpeningHours, placeRating,  placeWebsite,  placeImgUrl);
        this.status=status;
    }

    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
