package com.smartrack.smartrack.Model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class Model {
    public final static Model instance = new Model();
    String appID = "application-smartrack-lhtyu";
    App app = new App(new AppConfiguration.Builder(appID).build());
    UserModelMongoDB user= new UserModelMongoDB();
    private ModelTravelerServer travelerModelServer=new ModelTravelerServer();
    private ModelTravelerSQL travelerModelSQL=new ModelTravelerSQL();
    List<String>listFavoriteCategoriesOfTraveler;
    private Model(){}

    public User getCurrentUser()
    {
        return UserModelMongoDB.getCurrentUser();
    }
    public interface GetUserByIDsListener{
        void onComplete(User user);
    }


    public interface AddTravelerListener{
        void onComplete(String isSuccess);
    }
    public void addTraveler(final Traveler traveler,final List<FavoriteCategories> listFavoriteCategories,Context context ,final AddTravelerListener listener){
        travelerModelServer.addTraveler(traveler,listFavoriteCategories,context,listener);
    }
    public void getTravelerByEmailInDB(String travelerMail, Context context, final GetTravelerByEmailListener listener){

        travelerModelSQL.getTravelerByMail(travelerMail, context,listener);

    }
    public void getTravelerByEmailInServer(String travelerMail, Context context, final GetTravelerByEmailListener listener){

        travelerModelServer.getTraveler(travelerMail, context,listener);

    }
    public List<String> getAllFavoriteCategoriesOfTraveler(String travelerMail,Context context) {
        if(listFavoriteCategoriesOfTraveler==null){
            listFavoriteCategoriesOfTraveler=travelerModelSQL.getAllFavoriteCategoriesOfTraveler(travelerMail,context);
        }
        return  listFavoriteCategoriesOfTraveler;
    }
    public interface AddTravelerAndFavoriteCategoriesListener{
        void onComplete(boolean isSuccess);
    }
    public interface GetTravelerByEmailListener{
        void onComplete(Traveler traveler,List<String> favoriteCategories);
    }

}
