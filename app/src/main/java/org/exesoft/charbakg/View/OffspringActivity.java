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

import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Controller.LivestockController;
import org.exesoft.charbakg.Controller.OffspringController;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OffspringActivity extends AppCompatActivity {

    private  static String TAG = "OffspringActivity";
    private ImageButton addNew;
    private ListView offspringList;
    private String owner;
    private TextView sum;
    private EditText dateFromInput;
    private EditText dateToInput;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private ImageButton refreshBtn;
    private OffspringActivity activity;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offspring);
        activity = this;
        // Init add new button
        addNew = findViewById(R.id.addOffspring);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OffspringFormActivity.class);
                intent.putExtra("owner",getOwner());
                startActivity(intent);
                finish();
            }
        });
        // Init offspringList
        offspringList = findViewById(R.id.offspringView);
        AppToolbar.initSimpleToolbar(this, R.id.offspringToolbar);
        owner = getIntent().getStringExtra("owner");
        //Init sum TextView
        sum  = findViewById(R.id.offspringSum);
        // Init dateFromInput EditText
        dateFromInput = findViewById(R.id.offspringDateFromInput);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -12);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        dateFromInput.setText(formatter.format(cal.getTime()));
        dateFrom = cal.getTime();
        // Init dateToInput EditText
        dateToInput = findViewById(R.id.offspringDateToInput);
        dateToInput.setText(formatter.format(new Date()));
        // init dateFromBtn ImageButton
        dateFromBtn = findViewById(R.id.offspringDateFromBtn);
        dateFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date){
                        dateFrom = date;
                        dateFromInput.setText(new SimpleDateFormat("dd.MM.yyyy").format(date));
                    }
                });
            }
        });

        // init dateToBtn ImageButton
        dateToBtn = findViewById(R.id.offspringToDateBtn);
        dateToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date){
                        dateTo= date;
                        dateToInput.setText(new SimpleDateFormat("dd.MM.yyyy").format(date));
                    }
                });
            }
        });

        // Init refreshBtn
        refreshBtn = findViewById(R.id.offspringRefreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffspringController.offsprings(activity, dateFrom.getTime(), dateTo.getTime());
                Log.d(TAG,activity.getOwner());
            }
        });
        // Init progressBar ProgressBar;
        progressBar = findViewById(R.id.offspringProgressBar);
    }

    public  ListView getOffspringList(){
        return  offspringList;
    }

    public TextView getSum(){
        return  sum;
    }

    public ProgressBar getProgressBar(){
        return  progressBar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        OffspringController.offsprings(this, dateFrom.getTime(), dateTo.getTime());
        Log.d(TAG, "OffspringActivity Resumed");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

    public String getOwner(){
        return  owner;
    }
}

