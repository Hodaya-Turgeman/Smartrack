package com.smartrack.smartrack.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.smartrack.smartrack.Model.Gender;
import com.smartrack.smartrack.R;
import com.smartrack.smartrack.Model.Traveler;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.internal.objectstore.OsApp;
import io.realm.mongodb.User;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
public class newUserDetailsFragment extends Fragment {
    String Appid = "application-smartrack-zpmqf";
    private App app;

    public String mail;
    public String password;
    Spinner yearSpinner;
    int birthYear;
    static String[] years=new String[120];
    Calendar cal;
    private RadioGroup radioGroup;
    int gender;
    ListView listViewCategories;
    ArrayAdapter<String> categoryAdapter;
    String[] categories={
            "amusement park","aquarium","art gallery","bar","casino",
            "museum","night club","park","shopping mall","spa",
            "tourist attraction","zoo", "bowling alley","cafe",
            "church","city hall","library","mosque", "synagogue"
    };

    Button saveBtn;

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user_details, container, false);

        Realm.init(getActivity());

        app = new App(new AppConfiguration.Builder(Appid).build());
        years=getYears();
        yearSpinner=view.findViewById(R.id.new_user_year);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                birthYear=Integer.parseInt(years[position]);
                Log.d("TAG",String.valueOf(birthYear));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        radioGroup=view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_male:
                        Log.d("TAG","man");
                        gender=1;
                        break;
                    case R.id.radioButton_female:
                        Log.d("TAG","woman");
                        gender=2;
                        break;
                    case R.id.radioButton_other:
                        Log.d("TAG","other");
                        gender=3;
                        break;
                }
            }
        });

        listViewCategories=view.findViewById(R.id.new_user_categories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_multiple_choice,  categories);
        //categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listViewCategories.setAdapter(categoryAdapter);
        listViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                String itemSelected="";
                // Get user selected item.
                //Object itemObject = adapterView.getAdapter().getItem(itemIndex);
                for(int i=0; i<listViewCategories.getCount();i++){
                    if(listViewCategories.isItemChecked(i))
                    {
                        itemSelected+=listViewCategories.getItemAtPosition(i)+" , ";
                    }
                }
                Log.d("TAG",itemSelected);
//                // Get the checkbox.
//                CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.list_view_item_checkbox);
//                // Reverse the checkbox and clicked item check state.
//                if(itemDto.isChecked())
//                {
//                    itemCheckbox.setChecked(false);
//                    itemDto.setChecked(false);
//                }else
//                {
//                    itemCheckbox.setChecked(true);
//                    itemDto.setChecked(true);
//                }
            }
        });



        saveBtn=view.findViewById(R.id.add_traveler_button);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveTraveler(v);
            }
        });
        return view;
    }

    //   @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//        String itemSelected="";
//        for(int i=0; i<listViewCategories.getCount();i++){
//           if(listViewCategories.isItemChecked(i))
//           {
//               itemSelected+=listViewCategories.getItemAtPosition(i)+" , ";
//           }
//       }
//        Log.d("TAG",itemSelected);
//        return super.onOptionsItemSelected(item);
//    }
    public String[] getYears() {
        cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int s;
        for (int index = 0; index < years.length;) {
            s=year-index;
            years[index++] = String.valueOf(s);
            Log.d("TAG", years[index-1]);
        }
        return years;
    }
    private void saveTraveler(View v) {
        User user=app.currentUser();
        Log.d("TAG",user.getId());





//        /////////////////////////////////?////////////////
//        SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), PARTITION)
//                .allowQueriesOnUiThread(true)
//                .allowWritesOnUiThread(true)
//                .build();
//
//        Realm.getInstanceAsync(config, new Realm.Callback() {
//            @Override
//            public void onSuccess(Realm realm) {
//                Log.v(
//                        "EXAMPLE",
//                        "Successfully opened a realm with reads and writes allowed on the UI thread."
//                );
//            }
//        });
//        /////////////////////יצירה 1///////////////
        Traveler traveler = new Traveler();
//        backgroundThreadRealm.executeTransaction (transactionRealm -> {
//            transactionRealm.insert(traveler);
//        });
//        /////////////////////////יצירה 2///////////////////
//        realm.executeTransaction(r -> {
//            // Instantiate the class using the factory function.
//            Traveler traveler1 = r.createObject(Traveler.class, new ObjectId());
//            // Configure the instance.
//            turtle.setName("Max");
//            // Create a TurtleEnthusiast with a primary key.
//            ObjectId primaryKeyValue = new ObjectId();
//            TurtleEnthusiast turtleEnthusiast = r.createObject(TurtleEnthusiast.class, primaryKeyValue);
//        });

        //////////////////3////////////
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("smartrack");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Traveler");

        mongoCollection.insertOne(new Document("userid",user.getId()).append("data",traveler)).getAsync(result -> {
            if(result.isSuccess())
            {
                Log.v("Data","Data Inserted Successfully");
            }
            else
            {
                Log.v("Data","Error:"+result.getError().toString());
            }
        });

    }
}