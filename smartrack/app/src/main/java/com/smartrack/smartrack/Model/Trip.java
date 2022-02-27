package com.smartrack.smartrack.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Trip implements Parcelable {
    @PrimaryKey
    @NonNull
    private String id_trip;
    private String travelerMail;
    private String tripDestination;
    private String tripName;
    private int tripDaysNumber;

    @Ignore
    public Trip(){ }
    public Trip(String id_trip,String travelerMail,String tripDestination, String tripName,int tripDaysNumber) {
        this.id_trip = id_trip;
        this.tripDestination = tripDestination;
        this.tripName = tripName;
        this.tripDaysNumber = tripDaysNumber;
        this.travelerMail=travelerMail;
    }

    protected Trip(Parcel in) {
        id_trip = in.readString();
        travelerMail = in.readString();
        tripDestination = in.readString();
        tripName = in.readString();
        tripDaysNumber = in.readInt();
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_trip);
        dest.writeString(travelerMail);
        dest.writeString(tripDestination);
        dest.writeString(tripName);
        dest.writeInt(tripDaysNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}