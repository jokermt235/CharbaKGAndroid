package org.exesoft.charbakg.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.exesoft.charbakg.Adapter.ImageSliderAdapter;
import org.exesoft.charbakg.Controller.LivestockController;
import org.exesoft.charbakg.Model.Livestock;
import org.exesoft.charbakg.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LsformActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText serialNumberEdit;
    private EditText ageYear;
    private EditText ageMonth;
    private RadioGroup sexRadioGroup;
    private Toolbar appToolbar;
    private ViewPager imageSliderView;
    private ImageSliderAdapter imageSliderAdapter;
    private ImageButton cameraBtn;
    private static  final int CAMERA_REQUEST = 1111;
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageSliderAdapter.addItem(photo);
            imageSliderAdapter.notifyDataSetChanged();
        }
    }

    public Button getSaveButton() {
        return saveButton;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

class OnClickSave implements View.OnClickListener{

    private  LsformActivity lsformActivity;

    public OnClickSave(LsformActivity activity){
        this.lsformActivity = activity;
    }

    @Override
    public void onClick(View v) {
        Map<String, Object> livestock = new HashMap<>();
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
        LivestockController.save(lsformActivity, livestock ,lsformActivity.getImageSliderAdapter().getItems());
        lsformActivity.finish();
    }
}
