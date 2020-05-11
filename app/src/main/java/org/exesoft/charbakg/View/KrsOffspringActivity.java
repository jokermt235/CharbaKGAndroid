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
import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class KrsOffspringActivity extends AppCompatActivity {

    private static String TAG = "KrsOffspringActivity";
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
    private KrsOffspringActivity activity;
    private ProgressBar progressBar;
    private TextView sumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krs_offspring);
        activity = this;
        //Init offspringListView ListView
        offspringListView = findViewById(R.id.krsOffspringListView);
        //Init addOffspringBtn ImageButton
        addOffspringBtn = findViewById(R.id.krsOffspringAddBtn);
        addOffspringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), KrsOffspringFormActivity.class));
                finish();
            }
        });
        AppToolbar.initSimpleToolbar(this, R.id.krsOffspringToolbar);
        //Init dateFromInput EditText
        dateFromInput = findViewById(R.id.krsOffspringDateFromInput);
        dateFromInput.setText(DateInput.getDefaultFromDate());
        dateFrom = DateInput.getFromDateDefault();
        //Init dateToInput EditText
        dateToInput = findViewById(R.id.krsOffspringDateToInput);
        dateToInput.setText(DateInput.setDefaultToDate());
        //Init dateFromBtn ImageButton
        dateFromBtn = findViewById(R.id.krsOffspringDateFromBtn);
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
        dateToBtn = findViewById(R.id.krsOffspringToDateBtn);
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
        progressBar = findViewById(R.id.krsOffspringProgressBar);
        //Init sumText TextView
        sumText = findViewById(R.id.krsOffspringSum);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        sumText.setText("0");
        activity = this;
        SimpleLoader.filter("krs_offsprings",dateFrom.getTime(),dateTo.getTime(), new OnSimpleLoaderResult() {
            @Override
            public void onResult(ArrayList<Map<String, Object>> items){
                Log.d(TAG, "Result of elements is been recieved");
                if(items.size() == 0){
                    progressBar.setVisibility(View.GONE);
                }else{
                    if(items.size() > 0){
                        progressBar.setVisibility(View.GONE);
                        sumText.setText(Integer.toString(items.size()));
                        LivestockAdapter livestockAdapter = new LivestockAdapter(activity);
                        livestockAdapter.setItems(items);
                        offspringListView.setAdapter(livestockAdapter);
                    }
                }
            }
        });
        Log.d(TAG, "OnResume method");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public ListView getOffspringListView(){
        return  offspringListView;
    }

    public TextView getSumTextView(){
        return  sumTextView;
    }

}
