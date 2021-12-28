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
                    SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                            .allowQueriesOnUiThread(true)
                            .allowWritesOnUiThread(true)
                            .waitForInitialRemoteData(500, TimeUnit.MILLISECONDS)
                            .compactOnLaunch()
                            .build();
                    Realm.setDefaultConfiguration(config);
                    Realm.getInstanceAsync(config, new Realm.Callback() {
                        @Override
                        public void onSuccess(Realm realm) {
                            System.out.println("Successfully opened a realm.");
                            long userDetails = ModelMongoDB.getAmountUserDetailsWithId(user);
                            ModelMongoDB.closeRealm();
                            Intent intent;
                            if(userDetails==0)
                            {
                                intent = new Intent(SplashActivity.this, UserDetailsActivity.class);
                            }
                            else
                            {
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            }
                            startActivity(intent);
                            finish();
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