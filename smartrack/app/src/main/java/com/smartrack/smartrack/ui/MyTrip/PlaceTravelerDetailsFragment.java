package com.smartrack.smartrack.ui.MyTrip;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartrack.smartrack.Model.Model;
import com.smartrack.smartrack.Model.Place;
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.smartrack.smartrack.ui.profile.TravelerProfileFragmentDirections;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceTravelerDetailsFragment extends Fragment {

    Place place;
    ImageView placeImg;
    TextView placeName,placeAddress,placeOpeningHours,placeWebsite,placePhone,editRating;
    RatingBar ratingBar,ratingBarTraveler;
    double placeRating,placeTravelerRating;
    JSONObject jsonData=null;
    String jsonStringPlace;
    String destination;
    ProgressDialog myLoadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_place_traveler_details, container, false);
        place = PlaceTravelerDetailsFragmentArgs.fromBundle(getArguments()).getPlace();
        placeName=view.findViewById(R.id.fragment_place_traveler_details_place_name);
        placeAddress=view.findViewById(R.id.fragment_place_traveler_details_place_address);
        destination= PlaceTravelerDetailsFragmentArgs.fromBundle(getArguments()).getTripDestination();
        placeOpeningHours=view.findViewById(R.id.fragment_place_traveler_details_place_opening_hours);
        placeWebsite=view.findViewById(R.id.fragment_place_traveler_details_place_website);
        ratingBar=view.findViewById(R.id.fragment_place_traveler_details_place_rating);
        placeImg=view.findViewById(R.id.fragment_place_traveler_details_image);
        placePhone = view.findViewById(R.id.fragment_place_traveler_details_place_phone);
        ratingBarTraveler = view.findViewById(R.id.fragment_place_traveler_details_place_rating_user);
        Drawable drawable = ratingBarTraveler.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#2877B6"), PorterDuff.Mode.SRC_ATOP);
        editRating =view.findViewById(R.id.fragment_place_traveler_details_place_edit_rating_user);
        placeName.setText(place.getPlaceName());
        placeAddress.setText(place.getPlaceFormattedAddress());
        myLoadingDialog=new ProgressDialog(getContext());
        Model.instance.getOpenHoursOfPlace(place.getPlaceID(), getContext(), new Model.GetOpenHoursOfPlaceListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(List<String> result) {

                String openingHours = result.stream().map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n", "", ""));
                placeOpeningHours.setText(openingHours);
            }
        });
        String weblink = place.getPlaceWebsite();
        if(weblink!=null && weblink!="") {
            placeWebsite.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='"+weblink+"'> Link </a>";
            placeWebsite.setText(Html.fromHtml(text));
        }
        String phone= place.getPlaceInternationalPhoneNumber();
        if(phone!=null && phone!=""){
            placePhone.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href=\"tel:"+phone+"\""+">"+phone+"</a>";
            placePhone.setText(Html.fromHtml(text));
        }
        ratingBar.setRating(place.getPlaceRating());
        ratingBarTraveler.setRating(place.getTravelerRating());
        placeImg.setTag(place.getPlaceImgUrl());

        if (place.getPlaceImgUrl() != null && !place.getPlaceImgUrl().equals("")) {
            if (place.getPlaceImgUrl() == placeImg.getTag()) {
                Picasso.get().load(place.getPlaceImgUrl()).into(placeImg);
            }
        }
        editRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()) {
                    ShowDialog();
                }
                else{
                    Toast.makeText(getContext(), "Error! Connect to Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(getContext());

        LinearLayout linearLayout = new LinearLayout(getContext());
        final RatingBar rating = new RatingBar(getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        //add ratingBar to linearLayout
        linearLayout.addView(rating);


        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Edit Rating: ");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });

        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        myLoadingDialog.setTitle("Edit Rating");
                        myLoadingDialog.setMessage("Please wait,Edit your rating");
                        myLoadingDialog.setCanceledOnTouchOutside(false);
                        myLoadingDialog.show();
                        ratingBarTraveler.setRating((float) rating.getProgress());
                        place.setTravelerRating((float) rating.getProgress());
                        Model.instance.editPlace(place, destination, getContext(), new Model.EditPlaceListener() {
                            @Override
                            public void onComplete(boolean isSuccess) {
                                myLoadingDialog.dismiss();
                            }
                        });

                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }
}