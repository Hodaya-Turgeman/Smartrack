package com.smartrack.smartrack.ui.planTrip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SplashPlanTripFragment extends Fragment {
    JSONObject jsonDataPage1=null,jsonDataPage2=null,jsonDataPage3=null;
    String placeName;
    Integer tripDaysNumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shplash_plan_trip, container, false);
        placeName =SplashPlanTripFragmentArgs.fromBundle(getArguments()).getLocationTrip();
        tripDaysNumber=SplashPlanTripFragmentArgs.fromBundle(getArguments()).getTripDays();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                searchByTextInGooglePlaceApi(view);
            }


        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,5000);
        return view;
    }
    public void searchByTextInGooglePlaceApi(View view){

        Thread placeThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    String url="https://maps.googleapis.com/maps/api/place/textsearch/json?query="+placeName+"%20museom%20park";
                    //Page 1 in location search - 20 result in this page from google api places
                    OkHttpClient clientPage1 = new OkHttpClient().newBuilder()
                            .build();
                    String urlPage1=url+"&key="+getString(R.string.places_api_key);
                    Request requestPage1 = new Request.Builder()
                            .url(urlPage1)
                            .method("GET", null)
                            .build();
                    String next_page_token_page1="";
                    Response responsePage1 = clientPage1.newCall(requestPage1).execute();
                    final String dataPage1 = responsePage1.body().string();
                    jsonDataPage1 = new JSONObject(dataPage1);
                    if(jsonDataPage1.has("next_page_token"))
                        next_page_token_page1=jsonDataPage1.getString("next_page_token").toString();
                    //Page 2 in location search - 20 result in this page from google api places
                    if(next_page_token_page1!=null && next_page_token_page1!=""){
                        Thread.sleep(2000);
                        String urlPage2=url+"&pagetoken="+next_page_token_page1+ "&key="+getString(R.string.places_api_key);
                        Request requestPage2 = new Request.Builder()
                                .url(urlPage2)
                                .method("GET", null)
                                .build();
                        OkHttpClient clientPage2 = new OkHttpClient().newBuilder().build();
                        Response responsePage2 = clientPage2.newCall(requestPage2).execute();
                        final String dataPage2 = responsePage2.body().string();
                        jsonDataPage2 = new JSONObject(dataPage2);
                        //Page 2 in location search - 20 result in this page from google api places
                        if(jsonDataPage2.has("next_page_token")){
                            String next_page_token_page2="";
                            next_page_token_page2=jsonDataPage2.getString("next_page_token");
                            if(next_page_token_page2!=null && next_page_token_page2!=""){
                                Thread.sleep(2000);
                                String urlPage3=url+ "&pagetoken="+next_page_token_page2+"&key="+getString(R.string.places_api_key);
                                Request requestPage3 = new Request.Builder()
                                        .url(urlPage3)
                                        .method("GET", null)
                                        .build();
                                OkHttpClient clientPage3 = new OkHttpClient().newBuilder().build();
                                Response responsePage3 = clientPage3.newCall(requestPage3).execute();
                                final String dataPage3 = responsePage3.body().string();
                                jsonDataPage3 = new JSONObject(dataPage3);
                            }
                        }
                    }
                }catch (IOException | JSONException | InterruptedException exception){
                }
            }
        });
        placeThread.start();
        try {
            placeThread.join();
            JSONArray places=null;
            if(jsonDataPage1!=null) {
                places = (JSONArray) jsonDataPage1.getJSONArray("results");
                if(jsonDataPage2!=null){
                    JSONArray placesPage2=(JSONArray)jsonDataPage2.getJSONArray("results");
                    for(int i=0;i<placesPage2.length();++i){
                        places.put((JSONObject)placesPage2.get(i));
                    }
                    if(jsonDataPage3!=null) {
                        JSONArray placesPage3 = (JSONArray) jsonDataPage3.getJSONArray("results");
                        for (int j = 0; j < placesPage3.length(); ++j) {
                            places.put((JSONObject) placesPage3.get(j));
                        }
                    }

                }
                List<PlacePlanning> myPlaces=PlacesList.JsonArrayToListPlace(places);
                PlacePlanning[] arrayPlaces = new PlacePlanning[myPlaces.size()];
                myPlaces.toArray(arrayPlaces);
//                    myLoadingDialog.dismiss();
                SplashPlanTripFragmentDirections.ActionSplashPlanTripFragmentToPlacesListFragment action= SplashPlanTripFragmentDirections.actionSplashPlanTripFragmentToPlacesListFragment(arrayPlaces,tripDaysNumber);
                Navigation.findNavController(view).navigate(action);
            }
            System.out.println("Finish");
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }
}