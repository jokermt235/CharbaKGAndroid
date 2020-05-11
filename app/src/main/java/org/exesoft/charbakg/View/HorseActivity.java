package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Adapter.HorseAdapter;
import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Controller.HorseController;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class HorseActivity extends AppCompatActivity {
    private static String TAG = "HorseActivity";
    private ListView horseListView;
    private ImageButton addNewHorse;
    private EditText dateFromInput;
    private EditText dateToInput;
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private ImageButton refreshButton;
    private TextView sum;
    private HorseActivity activity;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private ProgressBar progressBar;
    private  Date dateFrom = new Date();
    private  Date  dateTo = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horse);
        activity  = this;
        // Init horseListView
        horseListView  = findViewById(R.id.horseListView);
        // Init newHorse ImageButton
        addNewHorse = findViewById(R.id.addHorseImageBtn);
        addNewHorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), HorseFormActivity.class));
            }
        });
        // Init sum
        sum = findViewById(R.id.horseSum);
        /*
            Init dateFromInput EditText ImageButton date picker
        */

        dateFromInput = findViewById(R.id.horseDateInput);
        dateFrom = DateInput.getFromDateDefault();
        dateFromInput.setText(DateInput.getFormatedDate(dateFrom));
        dateFromBtn  = findViewById(R.id.horseDateBtn);
        dateFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date){
                        dateFromInput.setText(DateInput.getFormatedDate(date));
                        dateFrom = date;
                    }
                });
            }
        });

        /*
            Init dateToInput EditText ImageButton date picker
        */

        dateToInput = findViewById(R.id.horseDateToInput);
        dateToInput.setText(formatter.format(new Date()));
        dateTo = new Date();
        dateToBtn  = findViewById(R.id.horseDateToBtn);
        dateToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date){
                        dateToInput.setText(DateInput.getFormatedDate(date));
                        dateTo = date;
                    }
                });
            }
        });

        //Init toolbar
        AppToolbar.initSimpleToolbar(this, R.id.horseToolbar);

        //Init progressBar ProgressBar
        progressBar = findViewById(R.id.horseProgressBar);
        //Init refreshButton ImageButton
        refreshButton = findViewById(R.id.horseRefreshBtn);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadLocal();
                Log.d(TAG, "Refresh button clicked");
            }
        });
    }

    public TextView getSum(){
        return sum;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public ListView getHorseListView(){
        return  horseListView;
    }

    public  ProgressBar getProgressBar(){
        return  progressBar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocal();
    }

    public void loadLocal(){
        progressBar.setVisibility(View.VISIBLE);
        sum.setText("0");
        SimpleLoader.filter("horses",dateFrom.getTime(), dateTo.getTime(),new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                super.onResult(items);
                HorseAdapter horseAdapter = new HorseAdapter(activity);
                horseAdapter.setItems(items);
                horseListView.setAdapter(horseAdapter);
                progressBar.setVisibility(View.GONE);
                sum.setText(Integer.toString(items.size()));
            }
        });
    }
}
