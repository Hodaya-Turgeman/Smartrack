package com.smartrack.smartrack.Model;
import android.content.Context;
import android.util.Log;

import com.smartrack.smartrack.HTTP.HttpCall;
import com.smartrack.smartrack.HTTP.HttpRequest;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModelTravelerServer {

    private ModelTravelerSQL travelerModelSQL=new ModelTravelerSQL();
    public void addTraveler(Traveler traveler, List<FavoriteCategories>listFavoriteCategories, Context context, Model.AddTravelerListener listener) {
        final String URL_PLAN_TRIP = "https://smartrack-app.herokuapp.com/traveler/addtraveler";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_PLAN_TRIP);
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
        final String URL_PLAN_TRIP = "https://smartrack-app.herokuapp.com/traveler/getinfotraveler";
        HttpCall httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.GET);
        httpCallPost.setUrl(URL_PLAN_TRIP);
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
                    String[] travelerFavoriteCategories = jsonTraveler.get("travelerFavoriteCategories").toString().split(",");
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

}
