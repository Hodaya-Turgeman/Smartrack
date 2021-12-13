package com.smartrack.smartrack.Model;

import org.bson.types.ObjectId;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class Traveler extends RealmObject {
    @PrimaryKey
    private ObjectId _id;
    @Required
    private Integer travelerBirthYear;
    @Required
    private RealmList<String> travelerFavoriteCategories;
    @Required
    private String travelerGender;
    @Required
    private String travelerMail;
    private String travelerName;

    public Traveler(){ }

    public Traveler(ObjectId _id,  String travelerMail,String travelerName, int travelerBirthYear, String travelerGender,RealmList<String> travelerFavoriteCategories) {
        this._id = _id;
        this.travelerName = travelerName;
        this.travelerBirthYear = travelerBirthYear;
        this.travelerGender = travelerGender;
        this.travelerMail=travelerMail;
        this.travelerFavoriteCategories = travelerFavoriteCategories;
    }
    // Standard getters & setters
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    public Integer getTravelerBirthYear() { return travelerBirthYear; }
    public void setTravelerBirthYear(Integer travelerBirthYear) { this.travelerBirthYear = travelerBirthYear; }
    public RealmList<String> getTravelerFavoriteCategories() { return travelerFavoriteCategories; }
    public void setTravelerFavoriteCategories(RealmList<String> travelerFavoriteCategories) { this.travelerFavoriteCategories = travelerFavoriteCategories; }
    public String getTravelerGender() { return travelerGender; }
    public void setTravelerGender(String travelerGender) { this.travelerGender = travelerGender; }
    public String getTravelerMail() { return travelerMail; }
    public void setTravelerMail(String travelerMail) { this.travelerMail = travelerMail; }
    public String getTravelerName() { return travelerName; }
    public void setTravelerName(String travelerName) { this.travelerName = travelerName; }


}

