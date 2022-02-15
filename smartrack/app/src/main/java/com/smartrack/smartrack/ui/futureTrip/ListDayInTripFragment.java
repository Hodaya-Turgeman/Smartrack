package com.smartrack.smartrack.ui.futureTrip;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.smartrack.smartrack.Model.PlacePlanning;
import com.smartrack.smartrack.R;
import com.smartrack.smartrack.ui.planTrip.PlacesListFragment;
import com.smartrack.smartrack.ui.planTrip.PlacesListFragmentArgs;
import com.squareup.picasso.Picasso;


public class ListDayInTripFragment extends Fragment {
    PlacePlanning[] arrayPlaces;
    Integer tripDays;
    ListView listViewPlaces;
    MyAdapter adapter;
    int [] arrDays;
    TextView numDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_day_in_trip, container, false);

        arrayPlaces = ListDayInTripFragmentArgs.fromBundle(getArguments()).getArrTripsDay();
        tripDays=  ListDayInTripFragmentArgs.fromBundle(getArguments()).getTripDays();
        arrDays = new int[tripDays];
//        for(int i=0;i<arrDays.length;++i){
//            arrDays[i]=i;
//        }
        listViewPlaces =view.findViewById(R.id.fragment_list_day_in_trip_list_view);
        adapter=new MyAdapter();
        listViewPlaces.setAdapter(adapter);
        return view;
    }
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (arrDays== null) {
                return 0;
            } else {
                return arrDays.length;
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
                view = inflater.inflate(R.layout.day_list_row, null);
            } else {

            }
            numDay = view.findViewById(R.id.day_list_row_textview);
            numDay.setText("Day "+(i+1));
            return view;
        }

    }
}