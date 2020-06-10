package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.exesoft.charbakg.Adapter.ImageSliderAdapter;
import org.exesoft.charbakg.Callback.OnSavedResult;
import org.exesoft.charbakg.Callback.OnSimpleLoaderResult;
import org.exesoft.charbakg.Controller.SimpleLoader;
import org.exesoft.charbakg.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LsformActivity extends AppCompatActivity {

    private LinearLayout saveButton;
    private EditText serialNumberEdit;
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sexRadioGroup;
    private Toolbar appToolbar;
    private ViewPager imageSliderView;
    private ImageSliderAdapter imageSliderAdapter;
    private ImageButton cameraBtn;
    private static  final int CAMERA_REQUEST = 1111;
    private RadioButton slaughter;
    private RadioButton slaughterNegative;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lsform);
        //Save button init
        saveButton = findViewById(R.id.LSFormSaveBtn);
        saveButton.setOnClickListener(new OnClickSave(this));
        //SerialNumber init
        serialNumberEdit = findViewById(R.id.LSFormNumber);
        //AgeEdit init
        ageYear = findViewById(R.id.LSFormAgeYear);
        ageMonth = findViewById(R.id.LSFormAgeMonth);
        //Init radio group;
        sexRadioGroup = findViewById(R.id.LSFormRadioGroup);
        imageSliderView = findViewById(R.id.LSFormBanner);
        imageSliderAdapter = new ImageSliderAdapter(getSupportFragmentManager());
        /*imageSliderAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_cow));
        imageSliderAdapter.addItem(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_horse));*/
        imageSliderView.setAdapter(imageSliderAdapter);
        //Male radio button
        // Init toolbar
        initToolbar();
        // init camera button
        cameraBtn = findViewById(R.id.LSFormUploadBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        slaughter = findViewById(R.id.lsFormSlaughter);
        slaughter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    slaughterNegative.setChecked(false);
                }
            }
        });
        progressBar = findViewById(R.id.progressBar);
        slaughterNegative = findViewById(R.id.lsFormSlaughterNegative);
        slaughterNegative.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    slaughter.setChecked(false);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageSliderAdapter.addItem(photo);
            imageSliderAdapter.notifyDataSetChanged();
        }
    }

    public LinearLayout getSaveButton() {
        return saveButton;
    }

    public RadioButton getSlaughterNegative(){
        return slaughterNegative;
    }

    public EditText getSerialNumberEdit() {
        return serialNumberEdit;
    }

    public EditText getAgeYear(){
        return ageYear;
    }

    public EditText getAgeMonth(){return  ageMonth;}

    public  RadioGroup getSexRadioGroup(){
        return sexRadioGroup;
    }

    public ImageSliderAdapter getImageSliderAdapter(){
        return  imageSliderAdapter;
    }

    public RadioButton getSlaughter(){
        return  slaughter;
    }

    private void initToolbar(){
        appToolbar = findViewById(R.id.lsformToolbar);
        appToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    public ProgressBar getProgressBar(){
        return progressBar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), LivestockActivity.class));
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadDefaults();
    }

    private void loadDefaults(){
        if(getIntent().getStringExtra("uid") != null){
            final String uid  = getIntent().getStringExtra("uid");
            SimpleLoader.filter("krs", "uid", uid, new OnSimpleLoaderResult(){
                @Override
                public void onResult(ArrayList<Map<String, Object>> items) {
                    super.onResult(items);
                    if(!items.isEmpty()){
                        Map<String, Object> livestock = items.get(0);
                        getSerialNumberEdit().setText(livestock.get("serial").toString());
                        getAgeYear().setText(livestock.get("ageYear").toString());
                        getAgeMonth().setText(livestock.get("ageMonth").toString());
                        RadioButton sex1 = findViewById(R.id.LSFromMale);
                        RadioButton sex2 = findViewById(R.id.LSFormFemale);
                        if(livestock.get("sex").toString().equals(sex1.getText())){
                            getSexRadioGroup().check(sex1.getId());
                        }else{
                            getSexRadioGroup().check(sex2.getId());
                        }
                        if((boolean)livestock.get("slaughter")){
                            getSlaughter().setChecked(true);
                        }else{
                            getSlaughterNegative().setChecked(true);
                        }
                    }
                }
            });
        }
    }
}

class OnClickSave implements View.OnClickListener{

    private  LsformActivity lsformActivity;

    public OnClickSave(LsformActivity activity){
        this.lsformActivity = activity;
    }

    @Override
    public void onClick(View v) {
        lsformActivity.getProgressBar().setVisibility(View.VISIBLE);
        Map<String, Object> livestock = new HashMap<>();
        try {
            String uid = UUID.randomUUID().toString();
            livestock.put("uid", uid);
            livestock.put("serial",lsformActivity.getSerialNumberEdit().getText().toString());
            String ageYear = lsformActivity.getAgeYear().getText().toString();
            String ageMonth = lsformActivity.getAgeMonth().getText().toString();
            livestock.put("ageYear", Integer.parseInt(ageYear.isEmpty() ? "0": ageYear));
            livestock.put("ageMonth",Integer.parseInt(ageMonth.isEmpty() ? "0":ageMonth));
            int sexView = lsformActivity.getSexRadioGroup().getCheckedRadioButtonId();
            RadioButton sex = lsformActivity.findViewById(sexView);
            livestock.put("sex",sex.getText().toString());
            livestock.put("added",new Date().getTime());
            livestock.put("slaughter", false);
            if(lsformActivity.getSlaughter().isChecked()){
                livestock.put("slaughter", true);
            }
            SimpleLoader.save("krs", livestock ,new OnSavedResult(){
                @Override
                public void onSave(boolean saved) {
                    super.onSave(saved);
                    if(saved){
                        lsformActivity.startActivity(new Intent(lsformActivity.getApplicationContext(), LivestockActivity.class));
                        lsformActivity.finish();
                        lsformActivity.getProgressBar().setVisibility(View.GONE);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(lsformActivity.getApplicationContext(), "Поля должны быть заполнены",Toast.LENGTH_LONG).show();
        }
    }
}
