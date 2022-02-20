package com.smartrack.smartrack.ui.planTrip;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smartrack.smartrack.HTTP.HttpCall;
import com.smartrack.smartrack.HTTP.HttpRequest;
import com.smartrack.smartrack.LoginActivity;
import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class PlacesListFragment extends Fragment {

    ListView listViewPlaces;
    MyAdapter adapter;
    TextView name,location;
    ImageView imagev;
    RatingBar rating;
    PlacePlanning[] arrayPlaces;
    ArrayList<PlacePlanning> chosenPlaces;
    Button button,planBtn;
    Integer tripDays,placesNum=0;
    TextView amountUserPlace;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        listViewPlaces =view.findViewById(R.id.fragment_places_list_listview);
        arrayPlaces =PlacesListFragmentArgs.fromBundle(getArguments()).getArrayPlaces();
        tripDays=PlacesListFragmentArgs.fromBundle(getArguments()).getTripDays();
        adapter=new MyAdapter();
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("TAG","post id"+i);
                PlacesListFragmentDirections.ActionPlacesListFragmentToPlaceDetailsFragment action=PlacesListFragmentDirections.actionPlacesListFragmentToPlaceDetailsFragment(arrayPlaces[i]);
                Navigation.findNavController(view).navigate( action);
            }
        });
        chosenNumber(arrayPlaces);
        amountUserPlace=view.findViewById(R.id.fragment_places_list_count_place_user);
        amountUserPlace.setText(String.valueOf(placesNum));

        planBtn=view.findViewById(R.id.fragment_places_list_planBtn);
        planBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(placesNum==0)
                    Toast.makeText(getContext(),"no places selected ",Toast.LENGTH_SHORT).show();
                else if (placesNum<tripDays)
                    Toast.makeText(getContext(),"Too few places selected ",Toast.LENGTH_SHORT).show();
                else if (placesNum > tripDays*3)
                    Toast.makeText(getContext(),"Too many places selected ",Toast.LENGTH_SHORT).show();
                else
                    CreateListForPlanning();
            }
        });
        return view;
    }

    private void chosenNumber(PlacePlanning[] arrayPlaces) {
        placesNum=0;
        for(int i=0; i<arrayPlaces.length;++i)
            if(arrayPlaces[i].getStatus()==true)
                placesNum++;
    }

    private void CreateListForPlanning() {
        chosenPlaces=new ArrayList<PlacePlanning>();
        for(int i=0;i<arrayPlaces.length;i++)
        {
            if(arrayPlaces[i].getStatus()==true)
                chosenPlaces.add(arrayPlaces[i]);
        }
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
//                    ArrayList<ArrayList<PlacePlanning>> array_place_days_planing = new ArrayList<>();
//                    for(int k=0; k<tripDays;++k){
//                        array_place_days_planing.add(new ArrayList<>());
//                    }
                    for (int j=0; j<chosenPlaces.size();++j){
                        String[] temp = arrOfStr[j].split("=");
                        chosenPlaces.get(j).setDay_in_trip(Integer.parseInt((temp[1]))+1);
//                        array_place_days_planing.get(Integer.parseInt((temp[1]))).add(chosenPlaces.get(j));

                    }
//                    for(int j=0;j< array_place_days_planing.size();++j){
//                        System.out.println("The trips in day: "+j);
//                        for(int i=0; i< array_place_days_planing.get(j).size();++i){
//                            System.out.print(array_place_days_planing.get(j).get(i).getPlaceName()+"   ,");
//                        }
//                        System.out.println();
//                    }


                    Toast.makeText(getActivity().getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    PlacePlanning[] arrayPlaces = new PlacePlanning[chosenPlaces.size()];
                    chosenPlaces.toArray(arrayPlaces);
                    PlacesListFragmentDirections.ActionPlacesListFragmentToListDayInTripFragment action=PlacesListFragmentDirections.actionPlacesListFragmentToListDayInTripFragment("aaa","bbbb",arrayPlaces ,tripDays);
                    Navigation.findNavController(getView()).navigate( action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                        if (s.equals("User added successfully")) {
//                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
//                        }
            }
        }.execute(httpCallPost);

    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (arrayPlaces== null) {
                return 0;
            } else {
                return arrayPlaces.length;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.place_list_row, null);
            } else {

            }
            PlacePlanning place=arrayPlaces[i];

            name = view.findViewById(R.id.place_list_row_name_trip);
            imagev = view.findViewById(R.id.place_list_row_image);
            button=view.findViewById(R.id.place_list_row_button);
            name.setText(place.getPlaceName());
            imagev.setTag(place.getPlaceImgUrl());
            rating=view.findViewById(R.id.place_list_row_rating);
            rating.setRating(place.getPlaceRating());
            Drawable drawable = rating.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FDC313"), PorterDuff.Mode.SRC_ATOP);
            if (place.getPlaceImgUrl() != null && place.getPlaceImgUrl() != "") {
                if (place.getPlaceImgUrl() == imagev.getTag()) {
                    Picasso.get().load(place.getPlaceImgUrl()).into(imagev);
                }
            } else {


            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(place.getStatus()==false){
                        place.setStatus(true);
                        placesNum++;
                        arrayPlaces[i].setStatus(true);
                        amountUserPlace.setText(String.valueOf(placesNum));

                    }
                    else{
                        place.setStatus(false);
                        placesNum--;
                        arrayPlaces[i].setStatus(false);
                        amountUserPlace.setText(String.valueOf(placesNum));
                    }
                   notifyDataSetChanged();
                }
            });

            button.setTag(place.getStatus());
            if(place.getStatus()==false && String.valueOf(button.getTag())=="false"){
                button.setText("Add");
                button.setBackgroundColor(Color.BLUE);
            }
            else{
                button.setText("Remove");
                button.setBackgroundColor(Color.RED);
            }
            return view;
        }

    }
}