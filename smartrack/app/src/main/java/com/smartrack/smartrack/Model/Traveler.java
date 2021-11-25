package com.smartrack.smartrack.Model;
import androidx.annotation.NonNull;

import java.sql.ClientInfoStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Traveler extends RealmObject {
    @PrimaryKey
    private String travelerID;
    private String travelerMail;
    private String travelerPassword;
    private String travelerName;
    private int travelerBirthYear;
    private int travelerGender;
    private RealmList<String> travelerFavoriteCategories;

    public Traveler(){ }

    public Traveler(String travelerID, String travelerMail, String travelerPassword, String travelerName, int travelerBirthYear, int travelerGender, RealmList<String> travelerFavoriteCategories) {
        this.travelerID = travelerID;
        this.travelerMail = travelerMail;
        this.travelerPassword = travelerPassword;
        this.travelerName = travelerName;
        this.travelerBirthYear = travelerBirthYear;
        this.travelerGender = travelerGender;
        //this.travelerFavoriteCategories=new int[19];
        this.travelerFavoriteCategories = travelerFavoriteCategories;
    }


    public String getTravelerID() {
        return travelerID;
    }

    public void setTravelerID(String travelerID) {
        this.travelerID = travelerID;
    }

    public String getTravelerMail() {
        return travelerMail;
    }

    public void setTravelerMail(String travelerMail) {
        this.travelerMail = travelerMail;
    }

    public String getTravelerPassword() {
        return travelerPassword;
    }

    public void setTravelerPassword(String travelerPassword) {
        this.travelerPassword = travelerPassword;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public int getTravelerBirthYear() {
        return travelerBirthYear;
    }

    public void setTravelerBirthYear(int travelerBirthYear) {
        this.travelerBirthYear = travelerBirthYear;
    }

    public int isTravelerGender() {
        return travelerGender;
    }

    public void setTravelerGender(int travelerGender) {
        this.travelerGender = travelerGender;
    }

    public RealmList<String> getTravelerFavoriteCategories() {
        return travelerFavoriteCategories;
    }

    public void setTravelerFavoriteCategories(RealmList<String> travelerFavoriteCategories) {
        this.travelerFavoriteCategories = travelerFavoriteCategories;
    }
}

