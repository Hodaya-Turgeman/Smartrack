package com.smartrack.smartrack.ui.profile;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.smartrack.smartrack.Model.Traveler;
import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;

import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class TravelerProfileFragment extends Fragment {
    TextView name, mail,categories;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_traveler_profile, container, false);
        Realm.init(getContext()); // context, usually an Activity or Application
        App app = new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        User user = app.currentUser();
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();

        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(config);
//        Traveler traveler = query.findFirstAsync();
//        Traveler traveler = realm.where(Traveler.class)
//                .equalTo("_id", new ObjectId(user.getId())).findFirstAsync();
//
//        name=view.findViewById(R.id.traveler_profile_name);
//        name.setText(traveler.getTravelerName());
//
//        mail=view.findViewById(R.id.traveler_profile_email);
//        mail.setText(traveler.getTravelerMail());
//
//        categories=view.findViewById(R.id.traveler_profile_categories);
//        String c= traveler.getTravelerFavoriteCategories().stream()
//                .map(n -> String.valueOf(n))
//                .collect(Collectors.joining("\n", "", ""));
//        categories.setText(c);



        return view;
    }
}