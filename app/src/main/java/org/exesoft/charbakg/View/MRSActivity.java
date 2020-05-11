package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Controller.MrsController;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MRSActivity extends AppCompatActivity {

    private static String TAG = "MRSActivity";
    private ListView mrsListView;
    private ImageButton addNewBtn;
    private TextView mrsSum;
    private Toolbar appToolbar;
    private ImageButton datePickBtn;
    private AppCompatActivity activity;
    private MRSActivity mrsActivity;
    private EditText dateInput;
    private EditText dateToInput;
    private ImageButton datePickToBtn;
    private ImageButton refreshBtn;
    private ProgressBar progressBar;
    private Date dateFrom = new Date();
    private Date dateTo  = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrs);
        //Init mrsListView
        mrsListView = findViewById(R.id.mrsListView);
        addNewBtn = findViewById(R.id.addMrsImageBtn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), MRSFormActivity.class));
                finish();
            }
        });
        // Init mrsSum
        mrsSum = findViewById(R.id.mrsSum);
        //Init toolbar
        AppToolbar.initSimpleToolbar(this, R.id.mrsToolbar);

        // Init date pickup button

        datePickBtn = findViewById(R.id.mrsDateBtn);

        activity = this;
        mrsActivity = this;

        // Init date input

        dateInput = findViewById(R.id.mrsDateInput);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        dateInput.setText(formatter.format(cal.getTime()));
        dateFrom = cal.getTime();

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void  onDateChoosen(Date date){
                        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                        dateInput.setText(formatter.format(date));
                        dateFrom = date;
                    }
                });
            }
        });

        // Init dateTo EditText
        dateToInput = findViewById(R.id.mrsDateToInput);
        dateToInput.setText(formatter.format(new Date()));
        dateTo = new Date();
        //Init dateToBtn ImageButton
        datePickToBtn = findViewById(R.id.mrsDateToBtn);

        datePickToBtn.setOnClickListener(new View.OnClickListener() {
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

        //Init refresh ImageButton

        refreshBtn = findViewById(R.id.mrsRefreshBtn);

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Refresh clicked");
                MrsController.mrs(mrsActivity,dateFrom.getTime(), dateTo.getTime());
            }
        });
        //Init progressBar ProgressBar
        progressBar = findViewById(R.id.mrsProgressBar);
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
        MrsController.mrs(this, dateFrom.getTime(), dateTo.getTime());
        Log.d(TAG, "OnResume method");
    }

    public TextView getMrsSum(){
        return mrsSum;
    }

    public ListView getMrsListView(){
        return  mrsListView;
    }

    public ProgressBar getProgressBar(){
        return  progressBar;
    }
}
