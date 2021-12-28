package com.smartrack.smartrack.Model;

import com.smartrack.smartrack.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class Model {
    public final static Model instance = new Model();
    String appID = "applcation-smartrack-zpmqf";
    App app = new App(new AppConfiguration.Builder(appID).build());
    UserModelMongoDB user= new UserModelMongoDB();

    private Model(){}

    public User getCurrentUser()
    {
        return UserModelMongoDB.getCurrentUser();
    }
    public interface GetUserByIDsListener{
        void onComplete(User user);
    }
    public Traveler getUserById(){
        return UserModelMongoDB.getTravelerById();
    }

}
