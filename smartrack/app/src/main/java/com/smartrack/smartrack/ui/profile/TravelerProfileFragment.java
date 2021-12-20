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

import com.google.android.gms.tasks.OnCompleteListener;
import com.smartrack.smartrack.Model.Traveler;
import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;

import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;


public class TravelerProfileFragment extends Fragment {

    TextView name, mail,categories;
    Button editBtn;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_traveler_profile, container, false);

        Realm.init(getContext()); // context, usually an Activity or Application
        App app = new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        User user = app.currentUser();
        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        Realm realm = Realm.getInstance(config);
        RealmResults<Traveler> travelers = realm.where(Traveler.class).equalTo("_id", new ObjectId(user.getId())).findAll();
        Traveler traveler= travelers.get(0);

        name=view.findViewById(R.id.traveler_profile_name);
        name.setText(traveler.getTravelerName());

        mail=view.findViewById(R.id.traveler_profile_email);
        mail.setText(traveler.getTravelerMail());

        categories=view.findViewById(R.id.traveler_profile_categories);
        String c= traveler.getTravelerFavoriteCategories().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n", "", ""));
        categories.setText(c);

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