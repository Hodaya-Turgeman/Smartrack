package com.smartrack.smartrack.Model;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.smartrack.smartrack.HTTP.HttpCall;
import com.smartrack.smartrack.HTTP.HttpRequest;
import com.smartrack.smartrack.ui.planTrip.PlacesListFragmentDirections;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModelTravelerServer {

    private ModelTravelerSQL travelerModelSQL=new ModelTravelerSQL();
    public void addTraveler(Traveler traveler, List<FavoriteCategories>listFavoriteCategories, Context context, Model.AddTravelerListener listener) {
        final String URL_ADD_TRIP = "https://smartrack-app.herokuapp.com/traveler/addtraveler";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_ADD_TRIP);
        HashMap<String, String> paramsTraveler = new HashMap<>();
        paramsTraveler.put("travelerMail",traveler.getTravelerMail());
        paramsTraveler.put("travelerName",traveler.getTravelerName());
        paramsTraveler.put("travelerBirthYear", String.valueOf(traveler.getTravelerBirthYear()));
        paramsTraveler.put("travelerGender",traveler.getTravelerGender());
        for (int i = 0; i < listFavoriteCategories.size() ; ++i) {
            paramsTraveler.put("travelerFavoriteCategories" + "[" + i + "]", listFavoriteCategories.get(i).getCategory());
        }
        httpCallPost.setParams(paramsTraveler);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("My Response:",response.toString());
                String result = response.toString();
                try {
                    travelerModelSQL.addTraveler(traveler, listFavoriteCategories, context, new Model.AddTravelerListener() {
                        @Override
                        public void onComplete(String isSuccess) {

                        }
                    });
                    listener.onComplete(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);

    }
    public void getTraveler(String travelerMail,Context context,Model.GetTravelerByEmailListener listener){
        final String URL_GET_TRAVELER = "https://smartrack-app.herokuapp.com/traveler/getinfotraveler";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_GET_TRAVELER);
        HashMap<String, String> paramsTraveler = new HashMap<>();
        paramsTraveler.put("travelerMail",travelerMail);
        httpCallPost.setParams(paramsTraveler);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("My Response:",response.toString());
                String result = response.toString();
                try {
                    JSONObject  jsonTraveler = new JSONObject(result);
                    Traveler traveler= new Traveler(jsonTraveler.get("travelerMail").toString(),jsonTraveler.get("travelerName").toString(),Integer.valueOf(jsonTraveler.get("travelerBirthYear").toString()),jsonTraveler.get("travelerGender").toString());
                    List<FavoriteCategories>listFavoriteCategories= new ArrayList<FavoriteCategories>();
                    String str = jsonTraveler.get("travelerFavoriteCategories").toString();
                    str = str.substring(1,str.length()-1);
                    String[] travelerFavoriteCategories = str.split(",");
                    for(int i=0 ;i< travelerFavoriteCategories.length;++i){
                        travelerFavoriteCategories[i] = travelerFavoriteCategories[i].substring(1, travelerFavoriteCategories[i].length() - 1);
                    }
                    for(int i=0; i<travelerFavoriteCategories.length;++i){
                        listFavoriteCategories.add(new FavoriteCategories(travelerFavoriteCategories[i],jsonTraveler.get("travelerMail").toString()));
                    }
                    travelerModelSQL.addTraveler(traveler, listFavoriteCategories, context, new Model.AddTravelerListener() {
                        @Override
                        public void onComplete(String isSuccess) {

                        }
                    });
                    listener.onComplete(traveler, Arrays.asList(travelerFavoriteCategories));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);

    }
    public void editTraveler(Traveler traveler, List<FavoriteCategories>listFavoriteCategories, Context context, Model.EditTravelerListener listener) {
        final String URL_EDIT_TRAVELER = "https://smartrack-app.herokuapp.com/traveler/editTraveler";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_EDIT_TRAVELER);
        HashMap<String, String> paramsTraveler = new HashMap<>();
        paramsTraveler.put("travelerMail",traveler.getTravelerMail());
        paramsTraveler.put("travelerName",traveler.getTravelerName());
        paramsTraveler.put("travelerBirthYear", String.valueOf(traveler.getTravelerBirthYear()));
        paramsTraveler.put("travelerGender",traveler.getTravelerGender());
        for (int i = 0; i < listFavoriteCategories.size() ; ++i) {
            paramsTraveler.put("travelerFavoriteCategories" + "[" + i + "]", listFavoriteCategories.get(i).getCategory());
        }
        httpCallPost.setParams(paramsTraveler);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("My Response:",response.toString());
                String result = response.toString();
                try {
                    travelerModelSQL.editTraveler(traveler, listFavoriteCategories, context, new Model.AddTravelerListener() {
                        @Override
                        public void onComplete(String isSuccess) {

                        }
                    });
                    listener.onComplete(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);

    }
    public void planTrip(ArrayList<PlacePlanning> chosenPlaces,int tripDays,Model.PlanTripListener listener ){
        final String URL_PLAN_TRIP = "https://smartrack-app.herokuapp.com/plantrip/samesizekmeans";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_PLAN_TRIP);
        HashMap<String, String> paramsPost = new HashMap<>();

        for (int i = 0; i < chosenPlaces.size(); ++i) {
            paramsPost.put("t" + "[" + i + "]", String.valueOf(chosenPlaces.get(i).getPlaceLocationLat()) + "," + String.valueOf(chosenPlaces.get(i).getPlaceLocationLng()));
        }
        paramsPost.put("numDayTrip", String.valueOf(tripDays));
        httpCallPost.setParams(paramsPost);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("My Response:",response.toString());
                String result = response.toString();
                try {
                    String[] arrOfStr = result.split(",");
                    for (int j=0; j<chosenPlaces.size();++j){
                        String[] temp = arrOfStr[j].split("=");
                        chosenPlaces.get(j).setDay_in_trip(Integer.parseInt((temp[1]))+1);

                    }
                 listener.onComplete(chosenPlaces);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);

    }
    public void addTrip(ArrayList<PlacePlanning> chosenPlaces,String tripName,String tripLocation,String travelerMail, Integer  tripDays, Model.AddTripListener listener) {
        final String URL_ADD_TRIP = "https://smartrack-app.herokuapp.com/traveler/addTrip";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_ADD_TRIP);
        HashMap<String, String> paramsPost = new HashMap<>();
        paramsPost.put("tripDaysNumber", String.valueOf(tripDays));
        paramsPost.put("tripName", tripName);
        paramsPost.put("travelerMail", travelerMail);
        paramsPost.put("tripDestination", tripLocation);
        for (int i = 0; i < chosenPlaces.size(); ++i) {
            String s="";
            s+=(chosenPlaces.get(i).getPlaceID()+",");
            s+=(chosenPlaces.get(i).getPlaceName()+",");
            s+=(String.valueOf(chosenPlaces.get(i).getPlaceLocationLat())+",");
            s+=(String.valueOf(chosenPlaces.get(i).getPlaceLocationLng())+",");
            s+=(chosenPlaces.get(i).getPlaceFormattedAddress()+",");
            s+=(chosenPlaces.get(i).getPlaceInternationalPhoneNumber()+",");
            s+=(String.valueOf(chosenPlaces.get(i).getPlaceRating())+",");
            s+=(chosenPlaces.get(i).getPlaceWebsite()+",");
            s+=(chosenPlaces.get(i).getPlaceImgUrl()+",");
            s+=(chosenPlaces.get(i).getDay_in_trip()+",");
            String openHours="";
            if(chosenPlaces.get(i).getPlaceOpeningHours()!=null){
                for (int j=0;j<chosenPlaces.get(i).getPlaceOpeningHours().size();++j){
                    openHours+=(chosenPlaces.get(i).getPlaceOpeningHours().get(j)+"#");
                }
            }
            s+=(openHours);
            paramsPost.put("arrChosenPlaces" + "[" + i + "]", s);
            System.out.println(paramsPost);
        }
        httpCallPost.setParams(paramsPost);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                Log.d("My Response:",response.toString());
                String result = response.toString();
                try {

                    listener.onComplete(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);


    }
    public void AddPlace(PlacePlanning chosenPlaces,String travelerMail,Model.AddPlaceListener listener){

    }
}
