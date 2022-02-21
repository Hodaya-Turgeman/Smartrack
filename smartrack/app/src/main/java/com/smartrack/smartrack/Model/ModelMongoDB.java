//package com.smartrack.smartrack.Model;
//import android.content.Context;
//import android.content.Intent;
//import android.media.VolumeShaper;
//import android.widget.Toast;
//
//import com.smartrack.smartrack.LoginActivity;
//import com.smartrack.smartrack.MainActivity;
//import com.smartrack.smartrack.R;
//
//import org.bson.types.ObjectId;
//import io.realm.Realm;
//import io.realm.RealmConfiguration;
//import io.realm.RealmQuery;
//import io.realm.RealmResults;
//import io.realm.mongodb.App;
//import io.realm.mongodb.AppConfiguration;
//import io.realm.mongodb.User;
//import io.realm.mongodb.sync.SyncConfiguration;
//
//public class ModelMongoDB {
//
//    final static String AppId = "application-smartrack-lhtyu";
//    static Realm realm=null;
//    public  static void closeRealm(){
//        if(!realm.isClosed())
//            realm.close();
//    }
////    public static Traveler getTraveler(Context context) {
////        if(realm!=null)
////            ModelMongoDB.closeRealm();
////        Realm.init(context); // context, usually an Activity or Application
////        App app = new App(new AppConfiguration.Builder(AppId).build());
////        User user = app.currentUser();
////        app.switchUser(user);
//////        realm = Realm.getDefaultInstance();
////        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
////                .allowQueriesOnUiThread(true)
////                .allowWritesOnUiThread(true)
////                .build();
////        config.shouldWaitForInitialRemoteData();
//////        SyncConfiguration config= SyncConfiguration.defaultConfig(user, user.getProfile().getEmail());
////        realm = Realm.getInstance(config);
////
//////        Traveler traveler = travelers.get(0);
////
////        realm.beginTransaction();
////        Traveler traveler = realm.where(Traveler.class).equalTo("_id", new ObjectId(user.getId())).findFirst();
////        realm.commitTransaction();
//////        Traveler traveler = travelers.get(0);
//////        config.shouldDeleteRealmOnLogout();
//////        realm.close();
////        return traveler;
//    }
//    public  static long getAmountUserDetailsWithId(User user){
//        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();
//        realm = Realm.getInstance(config);
//        RealmQuery<Traveler> travelerQuery = realm.where(Traveler.class);
//        config.shouldDeleteRealmOnLogout();
//      return travelerQuery.equalTo("_id", new ObjectId(user.getId())).count();
//    }
//}
