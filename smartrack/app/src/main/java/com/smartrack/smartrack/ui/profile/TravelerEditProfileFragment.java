package com.smartrack.smartrack.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.smartrack.smartrack.Model.Traveler;
import com.smartrack.smartrack.R;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.mongodb.User;
import io.realm.mongodb.UserProfile;


public class TravelerEditProfileFragment extends Fragment {
    Traveler traveler;

    TextInputLayout InputsName;
    Spinner genderSpinner, birthYearSpinner;
    TextView textViewCategory,errorCategory;
    boolean[] selectedCategory;
    Button saveBtn;
    ArrayList<Integer> categoriesList = new ArrayList<>();
    String travelerName,travelerGender;
    int travelerBirthYear;
    final String[] categoriesArray={
            "amusement park","aquarium","art gallery","bar","casino",
            "museum","night club","park","shopping mall","spa",
            "tourist attraction","zoo", "bowling alley","cafe",
            "church","city hall","library","mosque", "synagogue"
    };
    final String[] categoriesArraySaveInMongoDb={
            "amusement_park","aquarium","art_gallery","bar","casino",
            "museum","night club","park","shopping_mall","spa",
            "tourist_attraction","zoo", "bowling_alley","cafe",
            "church","city_hall","library","mosque", "synagogue"
    };
    User user;
    UserProfile userProfile;
    RealmList<String> travelerFavoriteCategories;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_traveler_edit_profile, container, false);

        //traveler=TravelerEditProfileFragmentArgs.fromBundle(getArguments()).getTraveler();

        return view;
    }
}