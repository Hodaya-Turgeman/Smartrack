package com.smartrack.smartrack.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelTravelerSQL {
    public List<Traveler> getTravelerByEmail(String travelerMail,Context context) {
        return AppLocalDB.getDatabase(context).travelerDao().getTravelerByEmail(travelerMail);
    }
    public List<Traveler> getAllTraveler(Context context){
        return AppLocalDB.getDatabase(context).travelerDao().getAllTraveler();
    }
    public List<String> getAllFavoriteCategoriesOfTraveler(String travelerMail,Context context){
        return  AppLocalDB.getDatabase(context).favoriteCategoriesDao().getAllFavoriteCategoriesOfTraveler(travelerMail);
    }
    public  void addTraveler(Traveler traveler,List<FavoriteCategories> favoriteCategories ,Context context,final Model.AddTravelerListener listener) {
        class MyAsynchTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.getDatabase(context).travelerDao().insertAll(traveler);
                for (int i=0;i<favoriteCategories.size();++i){
                    AppLocalDB.getDatabase(context).favoriteCategoriesDao().insertAll(favoriteCategories.get(i));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null) {
                    listener.onComplete("true");
                }
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute();

    }
    public void getTravelerByMail(final String travelerMail, Context context, final Model.GetTravelerByEmailListener listener) {
        class MyAsynchTask extends AsyncTask<String,String,String> {
            Traveler traveler;
            List<String>favoriteCategories;
            //will execute on the new thread
            @Override
            protected String doInBackground(String... strings) {
                favoriteCategories= AppLocalDB.getDatabase(context).favoriteCategoriesDao().getAllFavoriteCategoriesOfTraveler(travelerMail);
                List<Traveler>temp = AppLocalDB.getDatabase(context).travelerDao().getTravelerByEmail(travelerMail);
                if(temp.size()>0)
                    traveler=temp.get(0);

                return null;
            }

            //will execute on the main thread
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                listener.onComplete(traveler,favoriteCategories);
            }
        }
        MyAsynchTask task = new MyAsynchTask();
        task.execute();
    }



}
