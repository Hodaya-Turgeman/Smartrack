package com.smartrack.smartrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.smartrack.smartrack.Model.Model;
import com.smartrack.smartrack.Model.Traveler;

import org.apache.commons.lang.ObjectUtils;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
                    Model.instance.getTravelerByEmailInDB(user.getProfile().getEmail(),getApplicationContext(), new Model.GetTravelerByEmailListener() {
                        @Override
                        public void onComplete(Traveler traveler, List<String> favoriteCategories) {
                            if(traveler!=null && favoriteCategories!=null){
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(SplashActivity.this, UserDetailsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    });

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