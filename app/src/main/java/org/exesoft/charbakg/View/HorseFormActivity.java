package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Controller.HorseController;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Model.Horse;
import org.exesoft.charbakg.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HorseFormActivity extends AppCompatActivity {

    private RadioGroup horseTypeGroup;
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sexGroup;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_form);
        //Init horseTypeGroup RadioGroup
        horseTypeGroup = findViewById(R.id.horseFormNameRG);
        //Init ageYear
        ageYear = findViewById(R.id.horseFormAgeYear);
        //Init ageMonth
        ageMonth = findViewById(R.id.horseFormAgeMonth);
        //Init Sex RadioGroup
        sexGroup = findViewById(R.id.horseFormRadioGroup);
        // Init save button
        saveBtn = findViewById(R.id.horseFormSaveBtn);
        saveBtn.setOnClickListener(new OnSaveHorse(this));
        AppToolbar.initSimpleToolbar(this,R.id.horseFormToolbar);
    }

    public EditText getAgeYear(){
        return  ageYear;
    }

    public EditText getAgeMonth(){
        return  ageMonth;
    }

    public RadioGroup getSexGroup(){
        return  sexGroup;
    }

    public RadioGroup getHorseTypeGroup(){return  horseTypeGroup;}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

class OnSaveHorse implements View.OnClickListener{
    private HorseFormActivity activity;
    @Override
    public void onClick(View v) {
        Map<String,Object> horse = new HashMap<>();
        int typeId = activity.getHorseTypeGroup().getCheckedRadioButtonId();
        RadioButton type = activity.findViewById(typeId);
        horse.put("name",type.getText());
        int sexId = activity.getSexGroup().getCheckedRadioButtonId();
        RadioButton sex = activity.findViewById(sexId);
        horse.put("sex",sex.getText().toString().isEmpty() ? "Жен.": sex.getText().toString());
        horse.put("added",new Date().getTime());
        horse.put("ageYear",Integer.parseInt(activity.getAgeYear().getText().toString().isEmpty() ? "0":activity.getAgeYear().getText().toString()));
        horse.put("ageMonth",Integer.parseInt(activity.getAgeMonth().getText().toString().isEmpty() ? "0":activity.getAgeMonth().getText().toString()));
        SimpleLoader.save("horses",horse, new OnSavedResult(){
            @Override
            public void onSave(boolean saved) {
                super.onSave(saved);
                if(saved){
                    activity.startActivity(new Intent(activity.getApplicationContext(), HorseActivity.class));
                    activity.finish();
                }
            }
        });
    }

    public OnSaveHorse(HorseFormActivity activity){
        this.activity = activity;
    }
}
