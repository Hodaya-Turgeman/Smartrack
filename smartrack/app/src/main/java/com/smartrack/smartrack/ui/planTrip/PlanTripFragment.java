package com.smartrack.smartrack.ui.planTrip;

import android.content.Intent;
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
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                myPlace=place;

//                Toast.makeText(this.GalleryViewModel, place.getName(), Toast.LENGTH_SHORT).show();
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
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                String s="https://maps.googleapis.com/maps/api/place/textsearch/json?query=";
                s+=(myPlace.getName()+"%20Park%20Museum");
                s+=("&key="+getString(R.string.places_api_key));
                Request request = new Request.Builder()
                        .url(s)
                        .method("GET", null)
                        .build();
                Thread placeThread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String next_page_token;
                            synchronized (this) {
                                Response response = client.newCall(request).execute();
                                final String data = response.body().string();
                                JSONObject json = new JSONObject(data);
                                System.out.println("Data: " + data);
                                next_page_token=json.getString("next_page_token").toString();
                                Log.d("Place", json.getString("results").toString());
                            }
                            synchronized (this){
//                                String s2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=";
//                                s2+=(next_page_token);
                                String s2="https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=CpQCAgEAAFxg8o-eU7_uKn7Yqjana-HQIx1hr5BrT4zBaEko29ANsXtp9mrqN0yrKWhf-y2PUpHRLQb1GT-mtxNcXou8TwkXhi1Jbk-ReY7oulyuvKSQrw1lgJElggGlo0d6indiH1U-tDwquw4tU_UXoQ_sj8OBo8XBUuWjuuFShqmLMP-0W59Vr6CaXdLrF8M3wFR4dUUhSf5UC4QCLaOMVP92lyh0OdtF_m_9Dt7lz-Wniod9zDrHeDsz_by570K3jL1VuDKTl_U1cJ0mzz_zDHGfOUf7VU1kVIs1WnM9SGvnm8YZURLTtMLMWx8-doGUE56Af_VfKjGDYW361OOIj9GmkyCFtaoCmTMIr5kgyeUSnB-IEhDlzujVrV6O9Mt7N4DagR6RGhT3g1viYLS4kO5YindU6dm3GIof1Q";
                                s2+=("&key="+getString(R.string.places_api_key));
                                Request request2 = new Request.Builder()
                                        .url(s2)
                                        .method("GET", null)
                                        .build();
                                Response response = client.newCall(request2).execute();
                                final String data = response.body().string();

                                JSONObject json = new JSONObject(data);
                                System.out.println("Data: " + data);
                                Log.d("Place2",json.getString("results").toString());

                            }

                        }catch (IOException | JSONException e){

                        }
                    }
                });

                placeThread.start();
                
            }
        });

        Button planBtn=view.findViewById(R.id.fragment_plan_trip_button_ok);
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlanTripFragmentDirections.actionNavPlanTripToPlaceDetailsFragment action=PlanTripFragmentDirections.actionNavPlanTripToPlaceDetailsFragment();

                Navigation.findNavController(view).navigate(PlanTripFragmentDirections.actionNavPlanTripToPlaceDetailsFragment());

            }
        });
        return view;
    }

    private void secNextPage(String next_page_token) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String s="https://maps.googleapis.com/maps/api/place/nearbysearch/json?pagetoken=";
        s+=(next_page_token);
        s+=("&key="+getString(R.string.places_api_key));
        Request request = new Request.Builder()
                .url(s)
                .method("GET", null)
                .build();
        Thread placeThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try(Response response = client.newCall(request).execute()){
                    final String data = response.body().string();
                    JSONObject json = new JSONObject(data);
                    System.out.println("Data: " + data);
                    Log.d("Place2",json.getString("results").toString());

                }catch (IOException | JSONException e){

                }
            }
        });
        placeThread.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}