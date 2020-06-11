package org.exesoft.charbakg.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Controller.LivestockController;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.awt.font.TextAttribute;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LivestockActivity extends AppCompatActivity {

    private static  String TAG = "LivestockActvity";

    private ListView livestockList;
    private TextView livestockSum;
    private ImageButton dateBtn;
    private ImageButton dateToBtn;
    private EditText dateInput;
    private EditText dateToInput;
    private ImageButton refreshBtn;
    private ProgressBar progressBar;
    private LivestockActivity activity;
    private Date dateFrom;
    private Date dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livestock);
        ImageButton addLivestockBtn =  findViewById(R.id.addKrsImageBtn);
        livestockList = findViewById(R.id.krsListView);
        //livestockList.setOnItemClickListener(new onItemClickListener(this));
        livestockSum = findViewById(R.id.krsSum);
        //Init dateInput EditText date picker
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        activity = this;
        // Init dateInput EditText
        dateInput = findViewById(R.id.krsDateInput);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);

        dateInput.setText(formatter.format(cal.getTime()));
        dateFrom = cal.getTime();
        dateBtn = findViewById(R.id.krsDateBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
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
        //Init dateToInput EditText
        dateToInput  = findViewById(R.id.krsDateToInput);
        dateToInput.setText(formatter.format(new Date()));
        dateTo = new Date();
        dateToBtn = findViewById(R.id.krsDateToBtn);
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

        addLivestockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LsformActivity.class));
                finish();
            }
        });
        // Init refresh Button
        refreshBtn = findViewById(R.id.krsRefreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LivestockController.livestocks(activity,dateFrom.getTime(),dateTo.getTime());
            }
        });
        //Init progressBar
        progressBar = findViewById(R.id.krsProgressBar);
        AppToolbar.initSimpleToolbar(this, R.id.krsToolbar);
        livestockListInt();
    }

    private void livestockListInt(){
        livestockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), LsformActivity.class);
                TextView uid = (TextView)findViewById(R.id.livestockUid);
                TextView serial = (TextView)findViewById(R.id.livestockSerial);
                intent.putExtra("uid", uid.getText().toString());
                Log.d(TAG, Integer.toString(position));
                //startActivity(intent);
                //finish();
            }
        });
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
        LivestockController.livestocks(this, dateFrom.getTime(),dateTo.getTime());
        Log.d(TAG, "OnResume method");
    }

    public ListView getLivestockList(){
        return  livestockList;
    }

    public TextView getLivestockSum(){return  livestockSum;}

    public ProgressBar getProgressBar(){
        return  progressBar;
    }
}

class onItemClickListener implements AdapterView.OnItemClickListener{
    private LivestockActivity activity;
    private static String TAG = "KrsAdapterOnItemClickListener";
    public onItemClickListener(LivestockActivity activity){
        this.activity = activity;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Item clicked" );
    }
}

