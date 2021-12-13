package com.smartrack.smartrack.ui.planTrip;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smartrack.smartrack.R;


public class PlacesListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        return view;
    }

//    class MyAdapter extends BaseAdapter {
//        @Override
//        public int getCount() {
//            if (viewModel.getList().getValue() == null) {
//                return 0;
//            } else {
//                return viewModel.getList().getValue().size();
//            }
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            if (view == null) {
//                LayoutInflater inflater = getLayoutInflater();
//                view = inflater.inflate(R.layout.post_list_row, null);
//            } else {
//
//            }
//            Post post = viewModel.getList().getValue().get(i);
//            name = view.findViewById(R.id.post_list_row_name);
//            imagev = view.findViewById(R.id.post_list_row_image);
//            location = view.findViewById(R.id.post_list_row_location);
//            name.setText(post.getName());
//            location.setText(post.getLocation());
//            imagev.setTag(post.getImageUrl());
//
//            if (post.getImageUrl() != null && post.getImageUrl() != "") {
//                if (post.getImageUrl() == imagev.getTag()) {
//                    Picasso.get().load(post.getImageUrl()).into(imagev);
//                }
//            } else {
//
//
//            }
//            return view;
//        }
//
//    }
}