package com.smartrack.smartrack.ui.planTrip;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.smartrack.smartrack.R;
import com.smartrack.smartrack.horizontalNumberPicker.HorizontalNumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PlanTripFragment extends Fragment {
    Place myPlace;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_trip, container, false);
        if (!Places.isInitialized()) {
            Places.initialize(this.getContext(),getString(R.string.places_api_key));
        }
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this.getContext());
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                this.getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS,Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                myPlace=place;
//            Toast.makeText(this.GalleryViewModel, place.getName(), Toast.LENGTH_SHORT).show();
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

                Thread placeThread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            String url="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
                            url+=(myPlace.getName()+"%20museom%20park");
                            LatLng placeLatLng= myPlace.getLatLng();
                            url+=("&key="+getString(R.string.places_api_key));

//                            String url="https://maps.googleapis.com/maps/api/place/photo?photo_reference=Aap_uECgd3KGRw1zDXrM2AqzeYsdZCm8boFEA77aUHZqnZSkiKY0wsqy0BtU54zYxQehr6YR6I9OGmAranUGNY1I04yPER6P8VIwtjkw3dlTI20nnv3NuU6DfFOizaIRb93P8uLpeLwcXqVxZk4QaVzV_CRC2CalgfYgeMAWEYkOHhJN1p5q&maxwidth=4000&maxheight=3000&key=AIzaSyBJRQaRXY6ZHdXFKC7akPpuTTI0sytMjH0";
                            Request request = new Request.Builder()
                                    .url(url)
                                    .method("GET", null)
                                    .build();
                            String next_page_token="";
                            Response response = client.newCall(request).execute();

                            final String data = response.body().string();
                            JSONObject json = new JSONObject(data);
                            System.out.println("Data: " + data);
                            if(json.has("next_page_token"))
                                next_page_token=json.getString("next_page_token").toString();
//                            Log.d("Place", json.getString("results").toString());
                            System.out.println("########################################################################################\n###########################################################################################");
                            if(next_page_token!=null && next_page_token!=""){
                                Thread.sleep(4000);
                                String s2="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
                                s2+=(myPlace.getName()+"%20museom%20park");
                                s2+="&pagetoken=";
                                s2+=(next_page_token);
                                s2+=("&key="+getString(R.string.places_api_key));
                                Request request2 = new Request.Builder()
                                        .url(s2)
                                        .method("GET", null)
                                        .build();
                                OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                                Response response2 = client2.newCall(request2).execute();
                                final String data2 = response2.body().string();
                                JSONObject json2 = new JSONObject(data2);
                                System.out.println("Data2: " + data2);
                                if(json2.has("next_page_token")){
                                    String next_page_token2="";
                                    next_page_token2=json2.getString("next_page_token");
                                    if(next_page_token2!=null && next_page_token2!=""){
                                        Thread.sleep(4000);
                                        String s3="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
                                        s3+=(myPlace.getName()+"%20museom%20park");
                                        s3+="&pagetoken=";
                                        s3+=(next_page_token2);
                                        s3+=("&key="+getString(R.string.places_api_key));
                                        Request request3 = new Request.Builder()
                                                .url(s3)
                                                .method("GET", null)
                                                .build();
                                        OkHttpClient client3 = new OkHttpClient().newBuilder().build();
                                        Response response3 = client3.newCall(request3).execute();
                                        final String data3 = response3.body().string();

                                        JSONObject json3 = new JSONObject(data3);
                                        System.out.println("Data3: " + data3);
                                    }
                                }
                            }
                        }catch (IOException | JSONException | InterruptedException exception){
                        }
                    }
                });
                placeThread.start();
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}