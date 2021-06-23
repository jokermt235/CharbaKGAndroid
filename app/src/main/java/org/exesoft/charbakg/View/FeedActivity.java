package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Adapter.FeedAdapter;
import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.Modal.FeedReport;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private static String TAG = "FeedActivity";
    private ListView feedList;
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private EditText dateFromInput;
    private EditText dateToInput;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private FeedActivity activity;
    private Button reportBtn;
    private ImageButton refreshBtn;
    private ProgressBar progressBar;
    private TextView sumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        activity = this;
        AppToolbar.initSimpleToolbar(this, R.id.feedToolbar);
        //Init dateFromInput EditText
        dateFromInput = findViewById(R.id.feedDateFrom);
        dateFromInput.setText(DateInput.getDefaultFromDate());
        dateFrom = DateInput.getFromDateDefault();
        //Init dateToInput EditText
        dateToInput = findViewById(R.id.feedDateTo);
        dateToInput.setText(DateInput.setDefaultToDate());
        //Init dateFromBtn ImageButton
        dateFromBtn = findViewById(R.id.feedDateFromBtn);
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
        //Init dateToBtn ImageButton
        dateToBtn = findViewById(R.id.feedDateToBtn);
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
        //Init reportButton Button
        reportBtn = findViewById(R.id.feedReportBtn);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedReport feedReport = new FeedReport(activity);
                feedReport.show();
            }
        });
        //Init refreshBtn ImageButton
        refreshBtn = findViewById(R.id.feedRefreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.loadLocal();
            }
        });
        //Init feedList ListView
        feedList  = findViewById(R.id.feedListView);
        //Init progressBar ProgressBar
        progressBar = findViewById(R.id.feedProgressBar);
        // Init sumText TextView
        sumText = findViewById(R.id.feedSum);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocal();
        Log.d(TAG, getIntent().getStringExtra("owner"));
    }

    public void loadLocal(){
        progressBar.setVisibility(View.VISIBLE);
        Map<String,Object> filter = new HashMap<>();
        filter.put("collection","feed_report");
        filter.put("owner", getIntent().getStringExtra("owner"));
        SimpleLoader.filter(filter,dateFrom.getTime(), dateTo.getTime(), new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                Log.d(TAG, "feed_report list");
                super.onResult(items);
                Log.d(TAG, items.toString());
                FeedAdapter adapter = new FeedAdapter(activity, items);
                feedList.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                sumText.setText(Integer.toString(items.size()));
            }
        });
    }

    public void setDateTo(Date date){
        this.dateTo = date;
    }
}
