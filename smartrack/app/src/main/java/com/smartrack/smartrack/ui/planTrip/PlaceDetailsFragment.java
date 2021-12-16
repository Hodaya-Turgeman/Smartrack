package com.smartrack.smartrack.ui.planTrip;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;

import java.util.stream.Collectors;

import io.realm.RealmList;


public class PlaceDetailsFragment extends Fragment {
    PlaceDetails place;
    ImageView placeImg;
    TextView placeName,placeAddress,placeOpeningHours,placeWebsite;
    RatingBar ratingBar;
    double placeRating;
    Button addBtn;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_place_details, container, false);
        String _id="123456";
        RealmList<String> opening_hours=new RealmList<>("day1","day2");
        String img="img1";
        place=new PlaceDetails(_id,"the place name",13.344,242.434,"the address of the place","48538485",opening_hours, (float) 3.4,"www.www.www",img);

        placeName=view.findViewById(R.id.fragment_place_details_place_name);
        placeName.setText(place.getPlaceName());

        placeAddress=view.findViewById(R.id.fragment_place_details_place_address);
        placeAddress.setText(place.getPlaceFormattedAddress());

        placeOpeningHours=view.findViewById(R.id.fragment_place_details_place_opening_hours);
        String openingHours = place.getPlaceOpeningHours().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n", "", ""));
        placeOpeningHours.setText(openingHours);

        placeWebsite=view.findViewById(R.id.fragment_place_details_place_website);
        placeWebsite.setText(place.getPlaceWebsite());

        ratingBar=view.findViewById(R.id.fragment_place_details_place_rating);
        placeRating=place.getPlaceRating();
        ratingBar.setRating((float)placeRating);

        addBtn=view.findViewById(R.id.fragment_place_details_btn_add_place_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





        return view;
    }
}