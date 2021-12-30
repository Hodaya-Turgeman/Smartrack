package com.smartrack.smartrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.smartrack.smartrack.Model.ModelMongoDB;
import com.smartrack.smartrack.Model.Traveler;

import org.bson.types.ObjectId;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Realm.init(this); // context, usually an Activity or Application
        App app=new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        User user = app.currentUser();




        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                            .allowQueriesOnUiThread(true)
                            .allowWritesOnUiThread(true)
                            .build();
                    Realm realm = Realm.getInstance(config);
                    RealmQuery<Traveler> travelerQuery = realm.where(Traveler.class);
                    config.shouldDeleteRealmOnLogout();
                    long userDetails = travelerQuery.equalTo("_id", new ObjectId(user.getId())).count();

                    if(userDetails==0)
                    {
                        realm.close();
                        Intent intent=new Intent(SplashActivity.this,UserDetailsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        realm.close();
                        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else{
                    Intent intent=new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,5000);
    }
}