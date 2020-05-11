package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Controller.OffspringController;
import org.exesoft.charbakg.Model.Offspring;
import org.exesoft.charbakg.R;

public class OffspringFormActivity extends AppCompatActivity {

    private EditText serialInput;
    private EditText serialParentInput;
    private EditText ageYearInput;
    private EditText ageMonthInput;
    private Button saveBtn;
    private RadioGroup sexRadioG;
    private String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offspring_form);
        // Init Serial EditText
        serialInput = findViewById(R.id.offspringFormSerial);
        // Init SerialParent EditText
        serialParentInput = findViewById(R.id.offspringFormParentSerial);
        // Init Sex RadioGroup choose
        sexRadioG = findViewById(R.id.offspringFormRadioGroup);
        // Init Save button
        saveBtn = findViewById(R.id.offspringFormSaveBtn);
        saveBtn.setOnClickListener(new OnOffspringSave(this));
        // Init Owner
        owner = getIntent().getStringExtra("owner");
        // Init AgeYear EditText
        ageYearInput = findViewById(R.id.offspringFormAgeYear);
        // Init AgeMonth EditText
        ageMonthInput = findViewById(R.id.offspringFormAgeMonth);
    }

    public EditText getSerialInput(){
        return  serialInput;
    }

    public  EditText getSerialParentInput(){
        return  serialParentInput;
    }

    public  RadioGroup getSexRadioG(){
        return  sexRadioG;
    }

    public String getOwner(){
        return owner;
    }

    public EditText getAgeYearInput(){
        return ageYearInput;
    }

    public EditText getAgeMonthInput(){
        return  ageMonthInput;
    }
}

class OnOffspringSave implements View.OnClickListener{
    public OffspringFormActivity activity;

    public OnOffspringSave(OffspringFormActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Offspring offspring = new Offspring();
        if(!activity.getOwner().isEmpty()) {
            offspring.setOwner(activity.getOwner());
            offspring.setSerial(activity.getSerialInput().getText().toString().isEmpty() ? "0":activity.getSerialInput().getText().toString());
            offspring.setSerialPrent(activity.getSerialParentInput().getText().toString().isEmpty() ? "0":activity.getSerialParentInput().getText().toString());
            offspring.setAgeYear(Integer.parseInt(activity.getAgeYearInput().getText().toString().isEmpty() ? "0":activity.getAgeYearInput().getText().toString()));
            offspring.setAgeMonth(Integer.parseInt(activity.getAgeMonthInput().getText().toString().isEmpty() ? "0":activity.getAgeMonthInput().getText().toString()));
            int sexRadioButtonId = activity.getSexRadioG().getCheckedRadioButtonId();
            RadioButton sexRadioButton = activity.findViewById(sexRadioButtonId);
            if(sexRadioButton != null){
                offspring.setSex(sexRadioButton.getText().toString());
            }else{
                offspring.setSex("Жен.");
            }
            OffspringController.save(offspring);
            Intent intent = new Intent(activity.getApplicationContext(), OffspringActivity.class);
            switch (activity.getOwner()){
                case "krs": intent.putExtra("owner","krs");break;
                case "mrs": intent.putExtra("owner","mrs");break;
                case "horse": intent.putExtra("owner","horse");break;
            }
            activity.startActivity(intent);
            activity.finish();
        }
    }
}