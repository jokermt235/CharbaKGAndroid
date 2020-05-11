package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Callback.KrsOffspringFormCallbacks.OffspringSaveBtnCallback;
import org.exesoft.charbakg.R;

public class KrsOffspringFormActivity extends AppCompatActivity {

    private Button saveBtn;
    private EditText serialInput;
    private EditText serialParentInput;
    private RadioGroup radioGroup;
    private EditText ageYearInput;
    private EditText ageMonthInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_offspring_form);
        //Init saveBtn Button
        saveBtn = findViewById(R.id.krsOffspringFormSaveBtn);
        saveBtn.setOnClickListener(new OffspringSaveBtnCallback(this));
        //Init serialInput EditText
        serialInput = findViewById(R.id.krsOffspringFormSerial);
        //Init serialParentInput EditText
        serialParentInput = findViewById(R.id.krsOffspringFormParentSerial);
        //Init radioGroup  RadioGroup
        radioGroup = findViewById(R.id.krsOffspringFormRadioGroup);
        //Init ageYearInput EditText
        ageYearInput = findViewById(R.id.krsOffspringFormAgeYear);
        //Init ageMonthInput EditText
        ageMonthInput  = findViewById(R.id.krsOffspringFormAgeMonth);
        AppToolbar.initSimpleToolbar(this, R.id.krsOffspringFormToolbar);
    }

    public EditText getSerialInput(){
        return serialInput;
    }

    public EditText getSerialParentInput(){
        return  serialParentInput;
    }

    public EditText getAgeYearInput(){
        return  ageYearInput;
    }

    public EditText getAgeMonthInput(){
        return  ageMonthInput;
    }

    public RadioGroup getRadioGroup(){
        return  radioGroup;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), KrsOffspringActivity.class));
        finish();
    }
}
