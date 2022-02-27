package com.smartrack.smartrack.ui.logout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.smartrack.smartrack.Model.Model;
import com.smartrack.smartrack.R;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

public class LogoutFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Realm.init(getContext());
        App app=new App(new AppConfiguration.Builder(getString(R.string.AppId)).build());
        User user = app.currentUser();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                user.logOutAsync( result -> {
                    if (result.isSuccess()) {
                        Model.instance.deleteTraveler(user.getProfile().getEmail(), getContext(), new Model.DeleteTravelerListener() {
                            @Override
                            public void onComplete(boolean isSuccess) {
                                if(isSuccess){
                                    Toast.makeText(getContext(),"Delete user  Succeeded",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        Navigation.findNavController(getView()).navigate(R.id.action_nav_logaut_to_loginActivity3);
                    } else {
                        Log.e("AUTH", result.getError().toString());
                    }
                });
//
            }
        };
        Handler handler=new Handler();
        handler.postDelayed(runnable,5000);

        return view;
    }
}