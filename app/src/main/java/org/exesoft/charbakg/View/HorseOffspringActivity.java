package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.util.Date;

public class HorseOffspringActivity extends AppCompatActivity {

    private EditText dateFromInput;
    private EditText dateToInput;
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private ListView  horseOffspringList;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private ProgressBar progressBar;
    private TextView sum;
    private ImageButton refeshBtn;
    private ImageButton addHorseOffspringBtn;
    private HorseOffspringActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse_offspring);
        activity = this;
        //Init dateFormInput EditText
        dateFromInput  = findViewById(R.id.horseOffspringDateFromInput);
        dateFromInput.setText(DateInput.getDefaultFromDate());
        dateFrom = DateInput.getFromDateDefault();
        //Init dateToInput EditText
        dateToInput = findViewById(R.id.horseOffspringDateToInput);
        dateToInput.setText(DateInput.setDefaultToDate());
        dateTo = new Date();
        //Init dateFormBtn ImageButton
        dateFromBtn = findViewById(R.id.horseOffspringDateFromBtn);
        dateFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date) {
                        super.onDateChoosen(date);
                        dateFrom = date;
                        dateFromInput.setText(DateInput.getFormatedDate(date));
                    }
                });
            }
        });
        //Init dateToBtn ImageBtn
        dateToBtn = findViewById(R.id.horseOffspringToDateBtn);
        dateToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date) {
                        super.onDateChoosen(date);
                        dateTo = date;
                        dateToInput.setText(DateInput.getFormatedDate(date));
                    }
                });
            }
        });
        //Init appToolbar  toolbar
        AppToolbar.initSimpleToolbar(this,R.id.horseOffspringToolbar);
        //Init horseOffspringList ListView
        horseOffspringList = findViewById(R.id.horseOffspringListView);
        //Init progressBar ProgressBar
        progressBar = findViewById(R.id.horseOffspringProgressBar);
        //Init sum TextView
        sum = findViewById(R.id.horseOffspringSum);
        //Init refreshBtn ImageButton
        refeshBtn = findViewById(R.id.horseOffspringRefreshBtn);
        refeshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Init addHorseOffspringBtn ImageButton
        addHorseOffspringBtn = findViewById(R.id.horseOffspringAddBtn);
        addHorseOffspringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HorseOffspringFormActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}
