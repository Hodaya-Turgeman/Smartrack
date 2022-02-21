package com.smartrack.smartrack.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

@Entity
public class FavoriteCategories {
    @PrimaryKey
    @NotNull
    private  String category;
    private String travelerMail;

    public FavoriteCategories(String category, String travelerMail){
        this.category=category;
        this.travelerMail= travelerMail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTravelerMail() {
        return travelerMail;
    }

    public void setTravelerMail(String travelerMail) {
        this.travelerMail = travelerMail;
    }
}
