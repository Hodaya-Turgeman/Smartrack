package com.smartrack.smartrack.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class OpenHours {
    @PrimaryKey
    @NonNull
    private String open;
    @PrimaryKey
    @NonNull
    private  String place_id;

    @Ignore
    public OpenHours(){

    }
    public OpenHours(String open, String place_id){
        this.open=open;
        this.place_id=place_id;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }
}
