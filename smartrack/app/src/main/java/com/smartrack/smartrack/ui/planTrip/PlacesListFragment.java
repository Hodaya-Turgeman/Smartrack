package com.smartrack.smartrack.ui.planTrip;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.smartrack.smartrack.LoginActivity;
import com.smartrack.smartrack.Model.PlaceDetails;
import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
                Navigation.findNavController(view).navigate(action);
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
        System.out.println(chosenPlaces.size());
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