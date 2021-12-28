package com.smartrack.smartrack.Model;

import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.objectstore.OsApp;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class UserModelMongoDB {
//    private static User user;
//    private static App app= new App(new AppConfiguration.Builder("applcation-smartrack-zpmqf").build());
    public UserModelMongoDB(){}

    public static User getCurrentUser() {
        App app= new App(new AppConfiguration.Builder("applcation-smartrack-zpmqf").build());
        User user=app.currentUser();
        return user;
    }


    public static Traveler getTravelerById() {
        App app= new App(new AppConfiguration.Builder("applcation-smartrack-zpmqf").build());
        User user=app.currentUser();
        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(config);
        Traveler traveler = realm.where(Traveler.class).equalTo("_id", new ObjectId(user.getId())).findFirst();
        return traveler;
    }
}
