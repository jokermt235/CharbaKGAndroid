package org.exesoft.charbakg.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.exesoft.charbakg.Controller.MrsController;
import org.exesoft.charbakg.Controller.MrsOffspringController;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MrsOffspringFormActivity extends AppCompatActivity {
    private static String TAG = "mrsOffspringFormActivity";
    private EditText serial;
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sex;
    private Button saveBtn;
    private Spinner species;
    private EditText count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrs_offspring_form);
        //Init mrsSerial
        serial = findViewById(R.id.MRSFormNumber);
        //Init mrsAgeYear
        ageYear = findViewById(R.id.mrsOffspringFormAgeYear);
        // Init mrsAgeMonth
        ageMonth = findViewById(R.id.mrsOffspringFormAgeMonth);
        //Init mrsSex
        sex  = findViewById(R.id.mrsOffspringFormRadioGroup);
        // Init mrs Save Button
        saveBtn = findViewById(R.id.mrsOffspringFormSaveBtn);
        saveBtn.setOnClickListener(new MrsOffspringSaveEvent(this));
        species = findViewById(R.id.mrsOffspringFormSpecies);
        count = findViewById(R.id.mrsOffspringFormCount);
    }

    public Spinner getSpecies() {
        return species;
    }

    public  EditText getAgeYear(){
        return  ageYear;
    }

    public EditText getAgeMonth(){
        return ageMonth;
    }

    public EditText getCount(){
        return ageMonth;
    }

    public RadioGroup getSex(){
        return  sex;
    }

    @Override
    protected void onResume() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onResume();
        species.setAdapter(null);
        db.collection("mrs_spicies")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        arrayList.add(document.getData());
                    }
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), arrayList, android.R.layout.simple_list_item_1,
                            new String[]{"name"},
                            new int[]{android.R.id.text1});
                    species.setAdapter(adapter);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

}

class MrsOffspringSaveEvent implements View.OnClickListener{

    private MrsOffspringFormActivity activity;

    public MrsOffspringSaveEvent(MrsOffspringFormActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Map<String, Object> offspring = new HashMap<>();
        Map<String,Object> map = (Map<String,Object>)activity.getSpecies().getSelectedItem();
        if(map != null)
            offspring.put("species",map.get("name").toString());
        offspring.put("ageYear",Integer.parseInt(activity.getAgeYear().getText().toString().isEmpty() ? "0": activity.getAgeYear().getText().toString()));
        offspring.put("ageMonth",Integer.parseInt(activity.getAgeMonth().getText().toString().isEmpty() ? "0": activity.getAgeMonth().getText().toString()));
        int sexView = activity.getSex().getCheckedRadioButtonId();
        RadioButton sex = activity.findViewById(sexView);
        offspring.put("sex",sex.getText().toString());
        offspring.put("added",new Date().getTime());
        offspring.put("slaughter", false);
        offspring.put("amount",Integer.parseInt(activity.getCount().getText().toString().isEmpty() ? "1": activity.getCount().getText().toString()));
        MrsOffspringController.save(activity, offspring);
    }
}
