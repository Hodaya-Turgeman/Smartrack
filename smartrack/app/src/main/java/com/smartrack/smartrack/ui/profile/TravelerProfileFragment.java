package com.smartrack.smartrack.ui.profile;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.smartrack.smartrack.MainActivity;
import com.smartrack.smartrack.Model.ModelMongoDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.smartrack.smartrack.Model.Traveler;
import com.smartrack.smartrack.R;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.sync.SyncConfiguration;
// Base Realm Packages
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
// Realm Authentication Packages
import io.realm.mongodb.User;
import io.realm.mongodb.Credentials;

// MongoDB Service Packages
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.MongoCollection;
// Utility Packages
import org.bson.Document;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class TravelerProfileFragment extends Fragment {
    TextView name, mail,categories;
    Button editBtn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_traveler_profile, container, false);
        categories = view.findViewById(R.id.traveler_profile_categories);
        editBtn = view.findViewById(R.id.traveler_profile_edit_btn);
        mail = view.findViewById(R.id.traveler_profile_email);
        name = view.findViewById(R.id.traveler_profile_name);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Realm.init(getContext()); // context, usually an Activity or Application
        App app = new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        User user = app.currentUser();
        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(config);
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Traveler traveler = realm.where(Traveler.class).equalTo("_id", new ObjectId(user.getId())).findFirst(); // returning null so doesn't enter inside if block
                    if (traveler != null && traveler.isLoaded()) {
                        if (traveler.getTravelerName() != null)
                            name.setText(traveler.getTravelerName());
                        if (traveler.getTravelerMail() != null)
                            mail.setText(traveler.getTravelerMail());
                        if (traveler.getTravelerFavoriteCategories() != null) {
                            String c = traveler.getTravelerFavoriteCategories().stream()
                                    .map(n -> String.valueOf(n))
                                    .collect(Collectors.joining("\n", "", ""));
                            categories.setText(c);

                        }
                    }
                }
            });
        }catch (Exception e){}
        realm.close();

        editBtn= view.findViewById(R.id.traveler_profile_edit_btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_nav_profile_to_travelerEditProfileFragment);
            }
        });



        return view;
    }


}