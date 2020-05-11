package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Controller.OffspringController;
import org.exesoft.charbakg.Model.Offspring;
import org.exesoft.charbakg.R;

public class KRSChildForm extends AppCompatActivity {

    private static String TAG = "KRSChildForm";
    private Button saveButton;
    private EditText serialNumber;
    private EditText serialNumberParent;
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sexRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krschild_form);
        //Init saveButton
        saveButton = findViewById(R.id.krsChildFormSaveBtn);
        saveButton.setOnClickListener(new OnClickSaveOffspring(this));
        //Init serialNumber
        serialNumber = findViewById(R.id.krsChildFormNumber);
        //Init age Year
        ageYear  = findViewById(R.id.krsChildFormAgeYear);
        //Init age Month
        ageMonth = findViewById(R.id.krsChildFormAgeMonth);
        //Init sex radio group
        sexRadioGroup = findViewById(R.id.krsChildFormRadioGroup);
        //Init serialParent
        serialNumberParent = findViewById(R.id.krsParentFormNumber);
    }

    public EditText getSerialNumber(){
        return  serialNumber;
    }

    public EditText getAgeYear(){
        return  ageYear;
    }

    public EditText getAgeMonth(){
        return  ageMonth;
    }

    public RadioGroup getSexRadioGroup(){
        return  sexRadioGroup;
    }

    public EditText getSerialNumberParent(){
        return serialNumberParent;
    }
}
class OnClickSaveOffspring implements View.OnClickListener{
    private KRSChildForm activity;

    public OnClickSaveOffspring(KRSChildForm activity){
        this.activity = activity;
    }
    @Override
    public void onClick(View view){
        Offspring offspring = new Offspring();
        String ageYear = activity.getAgeYear().getText().toString();
        String ageMonth = activity.getAgeMonth().getText().toString();
        offspring.setSerial(activity.getSerialNumber().getText().toString().isEmpty() ? "0":activity.getSerialNumber().getText().toString());
        offspring.setSerialPrent(activity.getSerialNumberParent().getText().toString().isEmpty() ? "0":activity.getSerialNumberParent().getText().toString());
        offspring.setAgeYear(Integer.parseInt(ageYear.isEmpty() ? "0": ageYear));
        offspring.setAgeMonth(Integer.parseInt(ageMonth.isEmpty() ? "0":ageMonth));
        int sexView = activity.getSexRadioGroup().getCheckedRadioButtonId();
        RadioButton sex = activity.findViewById(sexView);
        offspring.setSex(sex.getText().toString().isEmpty() ? "Жен.":sex.getText().toString());
        OffspringController.save(offspring);
        activity.finish();
    }
}
