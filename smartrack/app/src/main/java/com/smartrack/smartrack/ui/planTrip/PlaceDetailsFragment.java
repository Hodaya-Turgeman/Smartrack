package com.smartrack.smartrack.ui.planTrip;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
public class PlaceDetailsFragment extends Fragment {
    PlacePlanning placePlanning,placeFullDetails;
    ImageView placeImg;
    TextView placeName,placeAddress,placeOpeningHours,placeWebsite;
    RatingBar ratingBar;
    double placeRating;
    Button addBtn;
    JSONObject jsonData=null;
    String jsonStringPlace;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_place_details, container, false);

        placePlanning=PlaceDetailsFragmentArgs.fromBundle(getArguments()).getPlace();

        placeName=view.findViewById(R.id.fragment_place_details_place_name);
        placeAddress=view.findViewById(R.id.fragment_place_details_place_address);
        placeOpeningHours=view.findViewById(R.id.fragment_place_details_place_opening_hours);
        placeWebsite=view.findViewById(R.id.fragment_place_details_place_website);
        ratingBar=view.findViewById(R.id.fragment_place_details_place_rating);
        placeImg=view.findViewById(R.id.fragment_place_details_image);
        addBtn=view.findViewById(R.id.fragment_place_details_btn_add_place_btn);

        String placeId=placePlanning.getPlaceID();
        placeFullDetails=getPlaceDetailsById(placeId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        placeName.setText(placeFullDetails.getPlaceName());
        placeAddress.setText(placeFullDetails.getPlaceFormattedAddress());
        String openingHours = placeFullDetails.getPlaceOpeningHours().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n", "", ""));
        placeOpeningHours.setText(openingHours);
       // placeWebsite.setText(placeFullDetails.getPlaceWebsite());
        placeRating=placeFullDetails.getPlaceRating();
        ratingBar.setRating((float)placeRating);
        placeImg.setTag(placeFullDetails.getPlaceImgUrl());

        if (placeFullDetails.getPlaceImgUrl() != null && placeFullDetails.getPlaceImgUrl() != "") {
            if (placeFullDetails.getPlaceImgUrl() == placeImg.getTag()) {
                Picasso.get().load(placeFullDetails.getPlaceImgUrl()).into(placeImg);
            }
        }
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

    private PlacePlanning getPlaceDetailsById(String placeId) {
        PlacePlanning p=new PlacePlanning();
        Thread placeThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String url="https://maps.googleapis.com/maps/api/place/details/json?place_id=";
                    url+=placeId+"&key="+getString(R.string.places_api_key);
                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .method("GET", null)
                            .build();
                    Response response = client.newCall(request).execute();
                    jsonStringPlace= response.body().string();
                    jsonData = new JSONObject(jsonStringPlace);
                }catch (IOException | JSONException f){
                }
            }
        });
        placeThread.start();
        try {
            placeThread.join();
            if(jsonData!=null)
            {
                JSONObject result=(JSONObject)jsonData.get("result");
                p=PlacesList.JsonToPlace(result);
            }
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return p;
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