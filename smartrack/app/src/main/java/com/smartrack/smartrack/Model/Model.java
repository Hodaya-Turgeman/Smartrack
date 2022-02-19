package com.smartrack.smartrack.Model;

import com.smartrack.smartrack.R;

import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class Model {
    public final static Model instance = new Model();
    String appID = "application-smartrack-lhtyu";
    App app = new App(new AppConfiguration.Builder(appID).build());
    UserModelMongoDB user= new UserModelMongoDB();
    private ModelTravelerServer travelerModelServer=new ModelTravelerServer();

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

    public interface AddTravelerListener{
        void onComplete(String isSuccess);
    }
    public void addTraveler(final Traveler traveler,final AddTravelerListener listener){
        travelerModelServer.addTraveler(traveler,listener);
    }

}
