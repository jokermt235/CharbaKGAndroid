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
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MRSFormActivity extends AppCompatActivity {

    private static String TAG = "MRSFormActivity";
    private EditText mrsSerial;
    private EditText mrsAgeYear;
    private EditText mrsAgeMonth;
    private RadioGroup mrsSex;
    private Button mrsSaveBtn;
    private Spinner mrsSpicies;
    private EditText mrsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrsform);
        //Init mrsSerial
        mrsSerial = findViewById(R.id.MRSFormNumber);
        //Init mrsAgeYear
        mrsAgeYear = findViewById(R.id.MRSFormAgeYear);
        // Init mrsAgeMonth
        mrsAgeMonth = findViewById(R.id.MRSFormAgeMonth);
        //Init mrsSex
        mrsSex  = findViewById(R.id.MRSFormRadioGroup);
        // Init mrs Save Button
        mrsSaveBtn = findViewById(R.id.MRSFormSaveBtn);
        mrsSaveBtn.setOnClickListener(new MrsSaveEvent(this));
        mrsSpicies = findViewById(R.id.MRSFormSpicies);
        mrsCount = findViewById(R.id.MRSFormCount);
    }

    public EditText getMrsSerial() {
        return mrsSerial;
    }

    public EditText getMrsAgeYear() {
        return mrsAgeYear;
    }

    public EditText getMrsAgeMonth() {
        return mrsAgeMonth;
    }

    public RadioGroup getMrsSex(){
        return  mrsSex;
    }

    public Spinner getMrsSpicies(){
        return  mrsSpicies;
    }

    public EditText getMrsCount(){return  mrsCount;}

    @Override
    protected void onResume() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        super.onResume();
        mrsSpicies.setAdapter(null);
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
                    mrsSpicies.setAdapter(adapter);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}

class MrsSaveEvent implements View.OnClickListener{

    private MRSFormActivity activity;

    public MrsSaveEvent(MRSFormActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Map<String, Object> mrs = new HashMap<>();
        Map<String,Object> map = (Map<String,Object>)activity.getMrsSpicies().getSelectedItem();
        if(map != null)
            mrs.put("species",map.get("name").toString());
        mrs.put("ageYear",Integer.parseInt(activity.getMrsAgeYear().getText().toString().isEmpty() ? "0": activity.getMrsAgeYear().getText().toString()));
        mrs.put("ageMonth",Integer.parseInt(activity.getMrsAgeMonth().getText().toString().isEmpty() ? "0": activity.getMrsAgeMonth().getText().toString()));
        int sexView = activity.getMrsSex().getCheckedRadioButtonId();
        RadioButton sex = activity.findViewById(sexView);
        mrs.put("sex",sex.getText().toString());
        mrs.put("added",new Date().getTime());
        mrs.put("slaughter", false);
        mrs.put("amount",Integer.parseInt(activity.getMrsCount().getText().toString().isEmpty() ? "1": activity.getMrsCount().getText().toString()));
        MrsController.save(activity, mrs);
    }
}