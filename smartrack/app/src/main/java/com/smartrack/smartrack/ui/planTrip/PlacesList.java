package com.smartrack.smartrack.ui.planTrip;

import com.google.gson.JsonArray;
import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.R;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class PlacesList {
    public static final  String api_key_place="AIzaSyBJRQaRXY6ZHdXFKC7akPpuTTI0sytMjH0";
    public static List<PlaceDetails> JsonArrayToListPlace(JSONArray arrayPlace) throws JSONException {
        List<PlaceDetails> myList=new ArrayList<PlaceDetails>();
        final String url="https://maps.googleapis.com/maps/api/place/photo?photo_reference=";

        for(int i=0;i< arrayPlace.length();++i){
            String placeID=null;
            String placeName="", placeFormattedAddress="",placeInternationalPhoneNumber="",placeWebsite="",placeImgUrl="";
            double placeLocationLat=0,placeLocationLng=0;
            float placeRating=0;
            RealmList<String> placeOpeningHours=null;
            JSONObject place=(JSONObject)arrayPlace.get(i);
            if(place.has("name")){
               placeName= place.get("name").toString();
            }
            if(place.has("formatted_address")){
                placeFormattedAddress=place.get("formatted_address").toString();
            }
            if(place.has( "geometry")){
                JSONObject geometry=(JSONObject)place.get("geometry");
                if(geometry.has("location")){
                    JSONObject location=(JSONObject)geometry.get("location");
                    if(location.has("lat") && location.has("lng")){
                        placeLocationLat=location.getDouble("lat");
                        placeLocationLng=location.getDouble("lng");
                    }
                }
            }
            if(place.has("place_id")){
                placeID=place.get("place_id").toString();
            }
            if(place.has("photos" )){
                JSONArray photos=(JSONArray)place.get("photos");

                if(photos.length()>0){
                    JSONObject photo=(JSONObject)photos.get(0);
                    if(photo.has( "photo_reference")&& photo.has("width")&& photo.has("height")){
                        String myUrl=url+photo.get("photo_reference").toString()+"&maxwidth="+photo.get("width").toString()+"&maxheight="+photo.get("height").toString()+"&key="+api_key_place;
                        placeImgUrl=myUrl;
                    }
                }

            }
            if(place.has("rating")){
                placeRating=(float)place.getDouble("rating");
            }
            if( placeLocationLat==0 &&  placeLocationLng==0){
                System.out.println("Error");
            }
            else {
                PlaceDetails myPlace = new PlaceDetails(placeID, placeName, placeLocationLat, placeLocationLng, placeFormattedAddress, placeInternationalPhoneNumber, placeOpeningHours, placeRating, placeWebsite, placeImgUrl);
                myList.add(myPlace);
            }
        }
        return myList;
    }
//     "place_id" : "ChIJTy8ebfKiAhURRKp0tl8_eDk",
//             "plus_code" : {
//        "compound_code" : "QMF4+7V Ashdod",
//                "global_code" : "8G3PQMF4+7V"
//    },
//            "rating" : 4.5,
//            "reference" : "ChIJTy8ebfKiAhURRKp0tl8_eDk",
//            "types" : [ "tourist_attraction", "park", "point_of_interest", "establishment" ],
//            "user_ratings_total" : 193
//},
//        {
//        "business_status" : "OPERATIONAL",
//        "formatted_address" : "Ashdod, Israel",
//        "geometry" : {
//        "location" : {
//        "lat" : 31.8168821,
//        "lng" : 34.6473344
//        },
//        "viewport" : {
//        "northeast" : {
//        "lat" : 31.81823192989272,
//        "lng" : 34.64868422989272
//        },
//        "southwest" : {
//        "lat" : 31.81553227010727,
//        I/System.out:                   "lng" : 34.64598457010727
//        }
//        }
//        },
//        "icon" : "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/park-71.png",
//        "icon_background_color" : "#4DB546",
//        "icon_mask_base_uri" : "https://maps.gstatic.com/mapfiles/place_api/icons/v2/tree_pinlet",
//        "name" : "Парк Лакхиш",
//        "opening_hours" : {
//        "open_now" : true
//        },
//        "photos" : [
//        {
//        "height" : 3456,
//        "html_attributions" : [
//        "\u003ca href=\"https://maps.google.com/maps/contrib/100995579281806437413\"\u003eshalva1948\u003c/a\u003e"
//        ],
//        "photo_reference" : "Aap_uECQBOk-mBCXLc9Nn-Tuqa-rYp1PXTjuHYhuTlp4SC0hRR8m4WKc-WhzzCyjX5K63VjK_JGeUmhwXsWv-e1HkPyTe63cg5O-Ly17DalHu4N8Lqv3C8AbZn_1I6uEC1zOOY8piMN84cEl3I-ng8Z5TRPJw6c37Cs5tJg7UIR5XLzbNhsD",
//        "width" : 5184
//        }
//        ],
}
