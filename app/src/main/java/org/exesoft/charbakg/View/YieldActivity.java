package org.exesoft.charbakg.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.exesoft.charbakg.Adapter.YieldAdapter;
import org.exesoft.charbakg.Callback.OnDatePickerResult;
import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Component.DateInput;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.Modal.DatePicker;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class YieldActivity extends AppCompatActivity {

    private static  final String TAG = "YieldActivity";

    public View dialogContent;
    public  AlertDialog.Builder addDialog;
    private ListView yieldList;
    private String owner;
    private TextView yieldSum;
    private YieldActivity activity;
    private EditText dateFromInput;
    private EditText dateToInput;
    private ProgressBar progressBar;
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private double sum = 0.0;
    private ImageButton dateFromBtn;
    private ImageButton dateToBtn;
    private ImageButton refreshBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yield);
        activity = this;
        ImageButton addYield = findViewById(R.id.addYield);
        addDialog = new AlertDialog.Builder(this);
        dialogContent = LayoutInflater.from(this).inflate(R.layout.new_yield_form,null);
        addDialog.setView(dialogContent);
        final AlertDialog dialog = addDialog.create();
        // Init dialog
        addYield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        dialogContent.findViewById(R.id.newYieldFormCloseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialogContent.findViewById(R.id.newYieldSaveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> yield = new HashMap<>();
                yield.put("owner",getOwner());
                yield.put("added",new Date().getTime());
                EditText amount = dialogContent.findViewById(R.id.new_yield_amount);
                String amountValue = amount.getText().toString();
                yield.put("amount",amountValue.isEmpty() ? 0 : Double.parseDouble(amountValue));
                SimpleLoader.save("yields",yield, new OnSavedResult(){
                    @Override
                    public void onSave(boolean saved) {
                        super.onSave(saved);
                        if(saved) {
                            Log.d(TAG, getOwner());
                            dateTo = new Date();
                            dateToInput.setText(DateInput.setDefaultToDate());
                            localLoad();
                        }
                    }
                });
                dialog.cancel();
            }
        });

        // Init YieldList
        yieldList = findViewById(R.id.yieldView);
        //GET  owner
        owner = getIntent().getStringExtra("owner");
        //Init yieldSum
        yieldSum = findViewById(R.id.yieldSum);
        AppToolbar.initSimpleToolbar(this, R.id.yieldToolbar);
        //Init dateFromInput EditText
        dateFromInput  = findViewById(R.id.yieldDateFrom);
        dateFromInput.setText(DateInput.getDefaultFromDate());
        dateFrom = DateInput.getFromDateDefault();
        // Init dateToInput
        dateToInput = findViewById(R.id.yieldDateTo);
        dateToInput.setText(DateInput.setDefaultToDate());
        progressBar = findViewById(R.id.yieldProgressBar);
        // Init dateFromBtn ImageButton
        dateFromBtn = findViewById(R.id.yieldDateFromBtn);
        dateFromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date) {
                        super.onDateChoosen(date);
                        dateFromInput.setText(DateInput.getFormatedDate(date));
                        dateFrom = date;
                    }
                });
            }
        });
        //Init dateToBtn ImageButton
        dateToBtn = findViewById(R.id.yieldDateToBtn);
        dateToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker.pickupDate(activity, new OnDatePickerResult(){
                    @Override
                    public void onDateChoosen(Date date) {
                        super.onDateChoosen(date);
                        dateToInput.setText(DateInput.getFormatedDate(date));
                        dateTo = date;
                    }
                });
            }
        });
        //Init refreshBtn ImageButton
        refreshBtn = findViewById(R.id.yieldRefreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localLoad();
            }
        });

    }

    public TextView getYieldSum(){
        return yieldSum;
    }

    public String getOwner(){
        return owner;
    }

    public ListView getYieldList(){
        return yieldList;
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
        localLoad();
        Log.d(TAG, "OnResume method");
    }

    private void localLoad(){
        progressBar.setVisibility(View.VISIBLE);
        sum = 0.0;
        yieldList.setAdapter(null);
        Log.d(TAG,getOwner());
        Map<String,Object> filter = new HashMap<>();
        filter.put("collection","yields");
        filter.put("owner",getOwner());
        SimpleLoader.filter(filter,dateFrom.getTime(),dateTo.getTime(),new OnSimpleLoaderResult(){
            @Override
            public void onResult(ArrayList<Map<String, Object>> items) {
                super.onResult(items);
                progressBar.setVisibility(View.GONE);
                YieldAdapter  yieldAdapter = new YieldAdapter(activity);
                yieldAdapter.setItems(items);
                yieldList.setAdapter(yieldAdapter);
                for(Map<String,Object> item:  items){
                    sum += Double.parseDouble(item.get("amount").toString());
                }
                yieldSum.setText(Double.toString(sum));
            }
        });
    }
}
