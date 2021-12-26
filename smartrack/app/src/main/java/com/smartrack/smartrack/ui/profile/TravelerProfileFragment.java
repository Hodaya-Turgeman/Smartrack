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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.smartrack.smartrack.MainActivity;
import com.smartrack.smartrack.Model.ModelMongoDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.smartrack.smartrack.Model.Traveler;
import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;

import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;

public class TravelerProfileFragment extends Fragment {
    TextView name, mail,categories;
    Button editBtn;
    Traveler traveler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_traveler_profile, container, false);

//      RealmConfiguration config = new RealmConfiguration.Builder()
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();
        //       Traveler traveler = query.where(Traveler.class)
        //                .equalTo("_id", new ObjectId(user.getId())).findFirst();
//        Traveler traveler = realm.where(Traveler.class)
//                .equalTo("_id", new ObjectId(user.getId())).findFirstAsync();
//        Realm.init(getContext()); // context, usually an Activity or Application
//        App app = new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
//        User user = app.currentUser();
//        SyncConfiguration config = new SyncConfiguration.Builder(user, user.getProfile().getEmail())
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();

        traveler= ModelMongoDB.getTraveler(getContext());

        name=view.findViewById(R.id.traveler_profile_name);
        name.setText(traveler.getTravelerName());
//
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