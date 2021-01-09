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

import org.exesoft.charbakg.Adapter.LivestockAdapter;
import org.exesoft.charbakg.Adapter.MrsAdapter;
import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MrsOffspringActivity extends AppCompatActivity {
    private static String TAG = "MrsOffspringActivity";
    private ImageButton addOffspringBtn;
    private ListView offspringListView;
    private EditText dateFromInput;
    private EditText dateToInput;
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private ImageButton refreshBtn;
    private TextView sumTextView;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private MrsOffspringActivity activity = this;
    private ProgressBar progressBar;
    private TextView sumText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrs_offspring);
        //Init offspringListView ListView
        offspringListView = findViewById(R.id.mrsOffspringListView);
        //Init addOffspringBtn ImageButton
        addOffspringBtn = findViewById(R.id.mrsOffspringAddBtn);
        addOffspringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MrsOffspringFormActivity.class));
                finish();
            }
        });
        AppToolbar.initSimpleToolbar(this, R.id.mrsOffspringToolbar);
        //Init dateFromInput EditText
        dateFromInput = findViewById(R.id.mrsOffspringDateFromInput);
        dateFromInput.setText(DateInput.getDefaultFromDate());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        dateFromInput.setText(formatter.format(cal.getTime()));
        dateFrom = cal.getTime();
        //Init dateToInput EditText
        dateToInput = findViewById(R.id.mrsOffspringDateToInput);
        dateToInput.setText(DateInput.setDefaultToDate());
        //Init dateFromBtn ImageButton
        dateFromBtn = findViewById(R.id.mrsOffspringDateFromBtn);
        dateFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void  onDateChoosen(Date date){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        dateFromInput.setText(formatter.format(date));
                        dateFrom = date;
                    }
                });
            }
        });
        //Init dateToBtn ImageButton
        dateToBtn = findViewById(R.id.mrsOffspringToDateBtn);
        dateToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void  onDateChoosen(Date date){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        dateToInput.setText(formatter.format(date));
                        dateTo = date;
                    }
                });
            }
        });
        //Init progressBar ProgressBar
        progressBar = findViewById(R.id.mrsOffspringProgressBar);
        //Init sumText TextView
        sumText = findViewById(R.id.mrsOffspringSum);
        // Init refreshBtn ImageButton
        refreshBtn = findViewById(R.id.mrsOffspringRefreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localLoader();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        localLoader();
    }

    public void localLoader(){
        progressBar.setVisibility(View.VISIBLE);
        offspringListView.setAdapter(null);
        SimpleLoader.filter("mrs_offspring",dateFrom.getTime(),dateTo.getTime(), new OnSimpleLoaderResult() {
            @Override
            public void onResult(ArrayList<Map<String, Object>> items){
                Log.d(TAG, "Result of elements is been recieved");
                if(items.size() == 0){
                    progressBar.setVisibility(View.GONE);
                }else{
                    if(items.size() > 0){
                        progressBar.setVisibility(View.GONE);
                        sumText.setText(Integer.toString(items.size()));
                        MrsAdapter livestockAdapter = new MrsAdapter(activity);
                        livestockAdapter.setItems(items);
                        offspringListView.setAdapter(livestockAdapter);
                    }
                }
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
