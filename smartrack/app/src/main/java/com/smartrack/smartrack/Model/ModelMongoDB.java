package com.smartrack.smartrack.Model;
import android.content.Context;
import org.bson.types.ObjectId;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class ModelMongoDB {

    final static String AppId = "application-smartrack-zpmqf";
    static Realm realm=null;
    public  static void closeRealm(){
        if(!realm.isClosed())
            realm.close();
    }
    public static Traveler getTraveler(Context context) {
//        if(realm!=null)
//            ModelMongoDB.closeRealm();
//        Realm.init(context); // context, usually an Activity or Application
//        App app = new App(new AppConfiguration.Builder(AppId).build());
//        User user = app.currentUser();
//        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();
//        realm = Realm.getInstance(config);
        realm = Realm.getDefaultInstance();
        RealmResults<Traveler> travelers = realm.where(Traveler.class).findAll();
        Traveler traveler = travelers.get(0);
        //config.shouldDeleteRealmOnLogout();
        return traveler;
    }

    public static long getAmountUserDetailsWithId(User user){
//        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();
//        realm = Realm.getInstance(config);
        realm = Realm.getDefaultInstance();
        RealmQuery<Traveler> travelerQuery = realm.where(Traveler.class);
       // config.shouldDeleteRealmOnLogout();
      return travelerQuery.equalTo("_id", new ObjectId(user.getId())).count();
    }
}
