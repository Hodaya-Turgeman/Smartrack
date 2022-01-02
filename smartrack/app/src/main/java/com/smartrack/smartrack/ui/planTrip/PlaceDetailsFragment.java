package com.smartrack.smartrack.ui.planTrip;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.squareup.picasso.Picasso;


public class PlaceDetailsFragment extends Fragment {
    PlacePlanning placePlanning;
    PlaceDetails place;
    ImageView placeImg;
    TextView placeName,placeAddress,placeOpeningHours,placeWebsite;
    RatingBar ratingBar;
    double placeRating;
    Button addBtn;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_place_details, container, false);

        placePlanning=PlaceDetailsFragmentArgs.fromBundle(getArguments()).getPlace();
//        String _id="123456";
//        RealmList<String> opening_hours=new RealmList<>("day1","day2");
//        String img="img1";
//        place=new PlaceDetails(_id,"the place name",13.344,242.434,"the address of the place","48538485",opening_hours, (float) 3.4,"www.www.www",img);

        placeName=view.findViewById(R.id.fragment_place_details_place_name);
        placeName.setText(placePlanning.getPlaceName());

        placeAddress=view.findViewById(R.id.fragment_place_details_place_address);
        placeAddress.setText(placePlanning.getPlaceFormattedAddress());

//        placeOpeningHours=view.findViewById(R.id.fragment_place_details_place_opening_hours);
//        String openingHours = placePlanning.getPlaceOpeningHours().stream()
//                .map(n -> String.valueOf(n))
//                .collect(Collectors.joining("\n", "", ""));
//        placeOpeningHours.setText(openingHours);

//        placeWebsite=view.findViewById(R.id.fragment_place_details_place_website);
//        placeWebsite.setText(placePlanning.getPlaceWebsite());

        ratingBar=view.findViewById(R.id.fragment_place_details_place_rating);
        placeRating=placePlanning.getPlaceRating();
        ratingBar.setRating((float)placeRating);

        placeImg=view.findViewById(R.id.fragment_place_details_image);
        placeImg.setTag(placePlanning.getPlaceImgUrl());
        if (placePlanning.getPlaceImgUrl() != null && placePlanning.getPlaceImgUrl() != "") {
            if (placePlanning.getPlaceImgUrl() == placeImg.getTag()) {
                Picasso.get().load(placePlanning.getPlaceImgUrl()).into(placeImg);
            }
        }
        addBtn=view.findViewById(R.id.fragment_place_details_btn_add_place_btn);
        if(placePlanning.getStatus()==false){
            addBtn.setText("Add to your trip");
            addBtn.setBackgroundColor(Color.BLUE);
        }
        else{
            addBtn.setText("Remove from your trip");
            addBtn.setBackgroundColor(Color.RED);
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeChosen();
            }
        });







        return view;
    }

    private void placeChosen() {
        if(placePlanning.getStatus()==false){
            placePlanning.setStatus(true);
            addBtn.setText("Remove");
            addBtn.setBackgroundColor(Color.RED);
        }
        else{
            placePlanning.setStatus(false);
            addBtn.setText("Add");
            addBtn.setBackgroundColor(Color.BLUE);
        }
        addBtn.setTag(placePlanning.getStatus());

    }
}