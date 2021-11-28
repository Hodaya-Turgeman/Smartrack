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

import org.bson.types.ObjectId;

import java.util.Calendar;
import java.util.Collections;

import io.realm.Realm;
import io.realm.internal.objectstore.OsApp;
import io.realm.mongodb.sync.SyncConfiguration;

public class newUserDetailsFragment extends Fragment  implements AdapterView.OnItemSelectedListener{
    public String mail;
    public String password;
    Spinner yearSpinner;
    int birthYear;
    static String[] years=new String[120];
    Calendar cal;
    private RadioGroup radioGroup;
    private Button btnDisplay;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user_details, container, false);

        years=getYears();
        yearSpinner=(Spinner)view.findViewById(R.id.new_user_year);

        ArrayAdapter<CharSequence> adapter =  new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item,years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(adapter);
        yearSpinner.setOnItemSelectedListener(this);
//        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                birthYear=Integer.parseInt(years[position]);
//                Log.d("TAG",String.valueOf(birthYear));
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

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

//        listViewCategories=view.findViewById(R.id.new_user_categories);
//        categoryAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice,  categories);
//        listViewCategories.setAdapter(categoryAdapter);



//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    saveTraveler(v);
//            }
//        });
        return view;
    }


//    @Override
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
//        Traveler traveler = new Traveler(mail,password,birthYear,gender);
//        backgroundThreadRealm.executeTransaction (transactionRealm -> {
//            transactionRealm.insert(task);
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

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String birthYear=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}