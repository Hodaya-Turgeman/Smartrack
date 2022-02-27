package com.smartrack.smartrack.ui.planTrip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.smartrack.smartrack.horizontalNumberPicker.HorizontalNumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlanTripFragment extends Fragment {
    Place myPlace=null;
    Integer tripDaysNumber;
    ProgressDialog myLoadingDialog;
    ProgressBar progressBar;
    JSONObject jsonDataPage1=null,jsonDataPage2=null,jsonDataPage3=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_trip, container, false);
        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(),getString(R.string.places_api_key));
        }
        myLoadingDialog=new ProgressDialog(this.getContext());
        TextView tripDays=view.findViewById(R.id.et_number);
        progressBar=view.findViewById(R.id.fragment_plan_trip_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getActivity());
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                this.getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS,Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                myPlace=place;
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
//                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final HorizontalNumberPicker np_channel_nr = view.findViewById(R.id.np_channel_nr);
        // use value in your code
        final int nr = np_channel_nr.getValue();
        Button planTripButton=view.findViewById(R.id.fragment_plan_trip_button_ok);
        planTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myLoadingDialog.setTitle("Search Places");
//                myLoadingDialog.setMessage("Please Wait!");
//                myLoadingDialog.setCanceledOnTouchOutside(false);
//                myLoadingDialog.show();
                tripDaysNumber=Integer.parseInt(tripDays.getText().toString());
                PlanTripFragmentDirections.ActionNavPlanTripToSplashPlanTripFragment action=PlanTripFragmentDirections.actionNavPlanTripToSplashPlanTripFragment(tripDaysNumber,myPlace.getName(), (float)myPlace.getLatLng().latitude, (float)myPlace.getLatLng().longitude);
                    Navigation.findNavController(view).navigate(action);

            }});

        return view;
    }

}