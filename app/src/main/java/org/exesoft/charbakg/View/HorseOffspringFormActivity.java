package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HorseOffspringFormActivity extends AppCompatActivity {
    private static String TAG = "HorseOffspringFormActivity";
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sex;
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_offspring_form);
        //Init ageYear , ageMonth EditText
        ageYear = findViewById(R.id.horseOffspringFormAgeYear);
        ageMonth = findViewById(R.id.horseOffspringFormAgeMonth);
        //Init sex RadioGroup
        sex  = findViewById(R.id.horseOffspringFormSex);
        // Init saveBtn Button
        saveBtn = findViewById(R.id.horseOffspringFormSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        //Init toolbar
        AppToolbar.initSimpleToolbar(this,R.id.horseOffspringFormToolbar);
    }

    private void save(){
        Map<String, Object> offspring = new HashMap<>();
        offspring.put("ageYear",Integer.parseInt(ageYear.getText().toString().isEmpty() ? "0": ageYear.getText().toString()));
        offspring.put("ageMonth",Integer.parseInt(ageMonth.getText().toString().isEmpty() ? "0": ageMonth.getText().toString()));
        int sexView = sex.getCheckedRadioButtonId();
        RadioButton sex = findViewById(sexView);
        offspring.put("sex",sex.getText().toString());
        offspring.put("added",new Date().getTime());
        offspring.put("slaughter", false);
        SimpleLoader.save("horse_offspring", offspring,new OnSavedResult(){
            @Override
            public void onSave(boolean saved) {
                super.onSave(saved);
                startActivity(new Intent(getApplicationContext(), HorseOffspringActivity.class));
                finish();
            }
        });
    }
}
