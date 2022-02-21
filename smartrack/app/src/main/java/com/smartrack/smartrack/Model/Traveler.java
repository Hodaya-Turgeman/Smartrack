package com.smartrack.smartrack.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Traveler {
    @PrimaryKey
    @NonNull
    private String travelerMail;
    private Integer travelerBirthYear;
    private String travelerGender;
    private String travelerName;

    @Ignore
    public Traveler(){ }

    public Traveler(String travelerMail,String travelerName, int travelerBirthYear, String travelerGender) {
        this.travelerName = travelerName;
        this.travelerBirthYear = travelerBirthYear;
        this.travelerGender = travelerGender;
        this.travelerMail=travelerMail;
    }
    // Standard getters & setters

    public Integer getTravelerBirthYear() { return travelerBirthYear; }
    public void setTravelerBirthYear(Integer travelerBirthYear) { this.travelerBirthYear = travelerBirthYear; }
    public String getTravelerGender() { return travelerGender; }
    public void setTravelerGender(String travelerGender) { this.travelerGender = travelerGender; }
    public String getTravelerMail() { return travelerMail; }
    public void setTravelerMail(String travelerMail) { this.travelerMail = travelerMail; }
    public String getTravelerName() { return travelerName; }
    public void setTravelerName(String travelerName) { this.travelerName = travelerName; }

}

