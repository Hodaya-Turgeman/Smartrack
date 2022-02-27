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
import android.widget.Toast;

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
import com.google.android.material.textfield.TextInputLayout;
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
    TextInputLayout InputsTripName;
    Place myPlace=null;
    Integer tripDaysNumber;
    ProgressDialog myLoadingDialog;
    ProgressBar progressBar;
    String tripName;
    JSONObject jsonDataPage1=null,jsonDataPage2=null,jsonDataPage3=null;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_trip, container, false);
        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(),getString(R.string.places_api_key));
        }
        InputsTripName =view.findViewById(R.id.fragment_plan_trip_name);

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
                tripName=InputsTripName.getEditText().getText().toString();
                tripDaysNumber=Integer.parseInt(tripDays.getText().toString());
                if(checkName(tripName)&&myPlace!=null)
                {
                    PlanTripFragmentDirections.ActionNavPlanTripToSplashPlanTripFragment action=PlanTripFragmentDirections.actionNavPlanTripToSplashPlanTripFragment(tripDaysNumber,myPlace.getName(), (float)myPlace.getLatLng().latitude, (float)myPlace.getLatLng().longitude);
                    Navigation.findNavController(view).navigate(action);
                }
                else
                    Toast.makeText(getContext(),"please choose destination for the trip", Toast.LENGTH_SHORT).show();


//                searchByTextInGooglePlaceApi(v);
            }});

        return view;
    }

    private boolean checkName(String tripName) {
        if (tripName.length()>0)
            return true;
        Toast.makeText(getContext(), "please enter a name for the trip", Toast.LENGTH_SHORT).show();
        return false;
    }

//    public void searchByTextInGooglePlaceApi(View view){
//
//            Thread placeThread=new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try{
//
//                        String url="https://maps.googleapis.com/maps/api/place/textsearch/json?query="+myPlace.getName()+"%20museom%20park";
//                        //Page 1 in location search - 20 result in this page from google api places
//                        OkHttpClient clientPage1 = new OkHttpClient().newBuilder()
//                                .build();
//                        String urlPage1=url+"&key="+getString(R.string.places_api_key);
//                        Request requestPage1 = new Request.Builder()
//                                .url(urlPage1)
//                                .method("GET", null)
//                                .build();
//                        String next_page_token_page1="";
//                        Response responsePage1 = clientPage1.newCall(requestPage1).execute();
//                        final String dataPage1 = responsePage1.body().string();
//                        jsonDataPage1 = new JSONObject(dataPage1);
//                        if(jsonDataPage1.has("next_page_token"))
//                            next_page_token_page1=jsonDataPage1.getString("next_page_token").toString();
//                        //Page 2 in location search - 20 result in this page from google api places
//                        if(next_page_token_page1!=null && next_page_token_page1!=""){
//                            Thread.sleep(2000);
//                            String urlPage2=url+"&pagetoken="+next_page_token_page1+ "&key="+getString(R.string.places_api_key);
//                            Request requestPage2 = new Request.Builder()
//                                    .url(urlPage2)
//                                    .method("GET", null)
//                                    .build();
//                            OkHttpClient clientPage2 = new OkHttpClient().newBuilder().build();
//                            Response responsePage2 = clientPage2.newCall(requestPage2).execute();
//                            final String dataPage2 = responsePage2.body().string();
//                            jsonDataPage2 = new JSONObject(dataPage2);
//                            //Page 2 in location search - 20 result in this page from google api places
//                            if(jsonDataPage2.has("next_page_token")){
//                                String next_page_token_page2="";
//                                next_page_token_page2=jsonDataPage2.getString("next_page_token");
//                                if(next_page_token_page2!=null && next_page_token_page2!=""){
//                                    Thread.sleep(2000);
//                                    String urlPage3=url+ "&pagetoken="+next_page_token_page2+"&key="+getString(R.string.places_api_key);
//                                    Request requestPage3 = new Request.Builder()
//                                            .url(urlPage3)
//                                            .method("GET", null)
//                                            .build();
//                                    OkHttpClient clientPage3 = new OkHttpClient().newBuilder().build();
//                                    Response responsePage3 = clientPage3.newCall(requestPage3).execute();
//                                    final String dataPage3 = responsePage3.body().string();
//                                    jsonDataPage3 = new JSONObject(dataPage3);
//                                }
//                            }
//                        }
//                    }catch (IOException | JSONException | InterruptedException exception){
//                    }
//                }
//            });
//            placeThread.start();
//            try {
//                placeThread.join();
//                JSONArray places=null;
//                if(jsonDataPage1!=null) {
//                    places = (JSONArray) jsonDataPage1.getJSONArray("results");
//                    if(jsonDataPage2!=null){
//                        JSONArray placesPage2=(JSONArray)jsonDataPage2.getJSONArray("results");
//                        for(int i=0;i<placesPage2.length();++i){
//                            places.put((JSONObject)placesPage2.get(i));
//                        }
//                        if(jsonDataPage3!=null) {
//                            JSONArray placesPage3 = (JSONArray) jsonDataPage3.getJSONArray("results");
//                            for (int j = 0; j < placesPage3.length(); ++j) {
//                                places.put((JSONObject) placesPage3.get(j));
//                            }
//                        }
//
//                    }
//                    List<PlacePlanning> myPlaces=PlacesList.JsonArrayToListPlace(places);
//                    PlacePlanning[] arrayPlaces = new PlacePlanning[myPlaces.size()];
//                    myPlaces.toArray(arrayPlaces);
////                    myLoadingDialog.dismiss();
//                    PlanTripFragmentDirections.ActionNavPlanTripToPlacesListFragment action=PlanTripFragmentDirections.actionNavPlanTripToPlacesListFragment(arrayPlaces,tripDaysNumber);
//                    Navigation.findNavController(view).navigate(action);
//                }
//                System.out.println("Finish");
//            } catch (InterruptedException | JSONException e) {
//                e.printStackTrace();
//            }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

}